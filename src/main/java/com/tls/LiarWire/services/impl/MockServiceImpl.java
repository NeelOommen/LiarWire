package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.exceptions.InvalidMockConfig;
import com.tls.LiarWire.Utils;
import com.tls.LiarWire.dataModels.impl.ResponseConfig;
import com.tls.LiarWire.exceptions.MockNotFound;
import com.tls.LiarWire.factories.DelayServiceFactory;
import com.tls.LiarWire.factories.ResponsePickerFactory;
import com.tls.LiarWire.factories.ResponseProcessorFactory;
import com.tls.LiarWire.repositories.MockRepository;
import com.tls.LiarWire.services.DelayService;
import com.tls.LiarWire.services.EndpointMatcher;
import com.tls.LiarWire.services.MockService;
import com.tls.LiarWire.services.ProbabilityPicker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.time.Duration;


@Slf4j
@Service
@RequiredArgsConstructor
public class MockServiceImpl implements MockService {

    private final MockRepository mockRepository;
    private final ResponseProcessorFactory responseProcessorFactory;
    private final DelayServiceFactory delayServiceFactory;
    private final ResponsePickerFactory responsePickerFactory;
    private final EndpointMatcher endpointMatcher;

    @Override
    public Mono<ResponseEntity<Object>> executeMock(ServerHttpRequest request) {
        String apiConfigKey = Utils.getKeyWithMethodAndEndpoint(request.getMethod().name(), request.getURI().getPath());
        return endpointMatcher.getIdForPath(request.getMethod().name(), request.getURI().getPath())
                .flatMap(mockRepository::findByObjectId)
                .map(apiConfig -> {
                    long delayMs = 0;
                    if (null != apiConfig.getDelay()) {
                        DelayService delayService = delayServiceFactory.getDelayService(apiConfig.getDelay().getType());
                        double delayPercentile = delayService.getRandomPercentage();
                        delayMs = delayService.getDelayinMillisBasedOnPercentile(apiConfig, delayPercentile);
                    }
                    return Tuples.of(apiConfig, delayMs);
                })
                .flatMap(tuple -> Mono.delay(Duration.ofMillis(tuple.getT2())).thenReturn(tuple.getT1()))
                .map(apiConfig -> {
                    ProbabilityPicker responsePicker = responsePickerFactory.getProcessor(apiConfig.getResponsePickerType());
                    ResponseConfig responseConfig = responsePicker.pickObject(apiConfig.getResponseList());
                    return responseProcessorFactory.getProcessor(responseConfig.getResponseContentType()).generateResponse(responseConfig, request);
                })
                .onErrorResume(MockNotFound.class, mnc -> {
                    log.error("Mock Config not found: {}", mnc.getMessage());
                    return Mono.just(ResponseEntity.badRequest().body("Mock not found for path: " + apiConfigKey));
                })
                .onErrorResume(InvalidMockConfig.class, imc -> {
                    log.error("Invalid mock config: {}", imc.getMessage());
                    return Mono.just(ResponseEntity.badRequest().body("Error while executing mock: " + apiConfigKey));
                })
                .onErrorResume(Exception.class, e -> {
                    log.error("Error occured while executing mock", e);
                    return Mono.just(ResponseEntity.internalServerError().body("Error while executing mock: " + apiConfigKey));
                });
    }
}

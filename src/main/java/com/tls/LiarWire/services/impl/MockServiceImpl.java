package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.HttpUtilities;
import com.tls.LiarWire.dataModels.impl.ResponseConfig;
import com.tls.LiarWire.entity.MockApiConfig;
import com.tls.LiarWire.factories.DelayServiceFactory;
import com.tls.LiarWire.factories.ResponsePickerFactory;
import com.tls.LiarWire.factories.ResponseProcessorFactory;
import com.tls.LiarWire.repositories.MockRepository;
import com.tls.LiarWire.services.MockService;
import com.tls.LiarWire.services.ProbabilityPicker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Slf4j
@Service
@RequiredArgsConstructor
public class MockServiceImpl implements MockService {

    private final MockRepository mockRepository;
    private final ResponseProcessorFactory responseProcessorFactory;
    private final DelayServiceFactory delayServiceFactory;
    private final ResponsePickerFactory responsePickerFactory;

    @Override
    public Mono<ResponseEntity<Object>> executeMock(ServerHttpRequest request) {
        try{
            Mono<MockApiConfig> config = mockRepository.findByEndpointAndMethod(request.getURI().getPath(), request.getMethod().toString());

            return config.map(apiConfig -> {
                log.debug("something");

                if(null != apiConfig.getDelay()){
                    try {
                        delayServiceFactory.getDelayService(apiConfig.getDelay().getType()).delayResponse(apiConfig);
                    } catch (InterruptedException e) {
                        log.error("Thread was interrupted during delay, proceeding with response");
                    }
                }

                ProbabilityPicker responsePicker = responsePickerFactory.getProcessor(apiConfig.getResponsePickerType());

                ResponseConfig responseConfig = responsePicker.pickObject(apiConfig.getResponseList());

                return responseProcessorFactory.getProcessor(responseConfig.getResponseContentType()).generateResponse(responseConfig, request);
            });
        }
        catch (Exception e){
            log.error("Error occured while executing mock", e);
            return Mono.just(ResponseEntity.internalServerError().body("Error while executing mock"));
        }
    }
}

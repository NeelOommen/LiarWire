package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.HttpUtilities;
import com.tls.LiarWire.entity.MockApiConfig;
import com.tls.LiarWire.factories.ResponseProcessorFactory;
import com.tls.LiarWire.repositories.MockRepository;
import com.tls.LiarWire.services.MockService;
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
    private final ResponseProcessorFactory factory;

    @Override
    public Mono<String> getConfig(String endpoint) {
        Mono<MockApiConfig> configMono = mockRepository.findById(endpoint);
        return configMono.map(
                MockApiConfig::getResponseContentType
        );
    }

    @Override
    public Mono<ResponseEntity<Object>> executeMock(ServerHttpRequest request) {
        Mono<MockApiConfig> config = mockRepository.findByEndpointAndMethod(request.getURI().getPath(), request.getMethod().toString());

        return config.map(apiConfig -> {
            log.debug("something");
            return factory.getProcessor(apiConfig.getResponseContentType()).generateResponse(apiConfig, request);
        });
    }
}

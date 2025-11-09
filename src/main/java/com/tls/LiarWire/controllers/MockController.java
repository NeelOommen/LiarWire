package com.tls.LiarWire.controllers;

import com.tls.LiarWire.services.MockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MockController {

    private final MockService mockService;

    @RequestMapping(value = "/**", method = {RequestMethod.GET, RequestMethod.POST})
    public Mono<ResponseEntity<Object>> executeMock(ServerWebExchange exchange){
        String uri = exchange.getRequest().getURI().getPath();
        log.info("Endpoint: {}", uri);
        return mockService.executeMock(exchange.getRequest());
    }

}

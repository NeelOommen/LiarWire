package com.tls.LiarWire.services;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;

public interface MockService {

    Mono<ResponseEntity<Object>> executeMock(ServerHttpRequest request);

}

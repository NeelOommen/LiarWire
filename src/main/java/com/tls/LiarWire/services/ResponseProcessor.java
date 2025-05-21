package com.tls.LiarWire.services;

import com.tls.LiarWire.HttpUtilities;
import com.tls.LiarWire.entity.MockApiConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;

public abstract class ResponseProcessor {

    public ResponseEntity<Object> generateResponse(MockApiConfig config, ServerHttpRequest request){
        return new ResponseEntity<>(generateBody(config, request), generateHeaders(config, request),
                HttpUtilities.mapConfigToHttpResponseStatus(config));
    }

    public abstract HttpHeaders generateHeaders(MockApiConfig config, ServerHttpRequest request);
    public abstract Object generateBody(MockApiConfig config, ServerHttpRequest request);

    public abstract String getType();

}

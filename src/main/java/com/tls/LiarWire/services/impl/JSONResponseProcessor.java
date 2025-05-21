package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.entity.MockApiConfig;
import com.tls.LiarWire.services.ResponseProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JSONResponseProcessor extends ResponseProcessor {

    @Override
    public HttpHeaders generateHeaders(MockApiConfig config, ServerHttpRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        return httpHeaders;
    }

    @Override
    public Object generateBody(MockApiConfig config, ServerHttpRequest request) {
        return config.getResponse();
    }

    @Override
    public String getType() {
        return "json";
    }


}

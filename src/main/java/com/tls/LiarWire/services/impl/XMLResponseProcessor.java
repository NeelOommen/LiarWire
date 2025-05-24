package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.dataModels.impl.ResponseConfig;
import com.tls.LiarWire.services.ResponseProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class XMLResponseProcessor extends ResponseProcessor {

    @Override
    public HttpHeaders generateHeaders(ResponseConfig config, ServerHttpRequest request) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);

        return httpHeaders;
    }

    @Override
    public Object generateBody(ResponseConfig config, ServerHttpRequest request) {
        return config.getResponse();
    }

    @Override
    public String getType() {
        return "xml";
    }

}

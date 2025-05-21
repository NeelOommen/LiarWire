package com.tls.LiarWire.entity;

import com.tls.LiarWire.dataModels.DelayParameters;
import com.mongodb.lang.Nullable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "Mock-APIS")
@Data
public class MockApiConfig {

    @Id
    private String endpoint;

    private String httpMethod;

    //Request Configs

    private Map<String, String> requestHeaders;

    private String request;

    //Response Configs

    private String responseHttpStatus;

    private String responseContentType;

    private Map<String, String> responseHeaders;

    private String response;

    //Behaviour Parameters

    @Nullable
    private DelayParameters delay;

}

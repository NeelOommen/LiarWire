package com.tls.LiarWire.entity;

import com.tls.LiarWire.dataModels.impl.DelayParameters;
import com.mongodb.lang.Nullable;
import com.tls.LiarWire.dataModels.impl.ResponseConfig;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
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
    private String responsePickerType;

    List<ResponseConfig> responseList;

    //Behaviour Parameters

    @Nullable
    private DelayParameters delay;

}

package com.tls.LiarWire.dataModels.impl;

import com.tls.LiarWire.dataModels.Probability;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResponseConfig extends Probability {

    private String responseHttpStatus;

    private String responseContentType;

    private Map<String, String> responseHeaders;

    private String response;

}

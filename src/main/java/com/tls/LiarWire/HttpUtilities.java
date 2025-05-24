package com.tls.LiarWire;

import com.tls.LiarWire.dataModels.impl.ResponseConfig;
import com.tls.LiarWire.entity.MockApiConfig;
import org.springframework.http.HttpStatusCode;

public class HttpUtilities {

    private HttpUtilities(){
        //private constructor
    }

    public static HttpStatusCode mapConfigToHttpResponseStatus(ResponseConfig config){
        return HttpStatusCode.valueOf(Integer.parseInt(config.getResponseHttpStatus()));
    }

}

package com.tls.LiarWire.entity;

import com.tls.LiarWire.exceptions.InvalidMockConfig;
import com.tls.LiarWire.dataModels.impl.DelayParameters;
import com.mongodb.lang.Nullable;
import com.tls.LiarWire.dataModels.impl.ResponseConfig;
import com.tls.LiarWire.interfaces.Validatable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Document(collection = "Mock-APIS")
@Data
@Slf4j
public class MockApiConfig implements Validatable {

    @Id
    private ObjectId id;

    private String endpoint;

    private String pathMatchingRegex;

    private boolean pathMatchingRequired;

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

    @Override
    public void validate() {
        if(null == endpoint || endpoint.isBlank()){
            throw new InvalidMockConfig("Endpoint is mandatory");
        }

        if(pathMatchingRequired && (pathMatchingRegex == null || pathMatchingRegex.isBlank())){
            throw new InvalidMockConfig("Path matching regex missing for path matchable endpoint: " + endpoint);
        }

        if(pathMatchingRequired){
            try{
                Pattern.compile(pathMatchingRegex);
            }
            catch (PatternSyntaxException pse){
                log.error("Invalid regex: {}, for endpoint: {}", pathMatchingRegex, endpoint);
                throw new InvalidMockConfig("Invalid regex for endpoint " + endpoint + " : " + pse.getMessage());
            }
        }
    }
}

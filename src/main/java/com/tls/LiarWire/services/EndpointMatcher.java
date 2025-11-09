package com.tls.LiarWire.services;

import com.tls.LiarWire.Utils;
import com.tls.LiarWire.entity.MockApiConfig;
import com.tls.LiarWire.exceptions.MockNotFound;
import com.tls.LiarWire.repositories.MockRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
@Slf4j
public class EndpointMatcher {

    private final MockRepository mockRepository;

    //not making the registries final, might add support to listen to update triggers from mongoDB,
    // in case configs are updates, so registries can be updated at runtime
    private Map<Pattern, ObjectId> pathMatchingRegistry;

    private Map<String, ObjectId> directEndpointMatchingRegistry;

    public EndpointMatcher(MockRepository _mockRepository){
        this.mockRepository = _mockRepository;
        this.pathMatchingRegistry = new HashMap<>();
        this.directEndpointMatchingRegistry = new HashMap<>();
        this.updateRegistries();
    }

    public void updateRegistries(){
        Flux<MockApiConfig> apiConfigs = mockRepository.findAll();

        apiConfigs.subscribe(
                this::addConfigToRegistries,
                this::errorHandler,
                this::loadCompleteHandler
        );
    }

    public Mono<ObjectId> getIdForPath(String httpMethod, String uri){
        String apiKey = Utils.getKeyWithMethodAndEndpoint(httpMethod, uri);
        if(directEndpointMatchingRegistry.containsKey(apiKey)){
            return Mono.just(directEndpointMatchingRegistry.get(apiKey));
        }
        else {
            for (Map.Entry<Pattern, ObjectId> entry : pathMatchingRegistry.entrySet()) {
                Pattern pattern = entry.getKey();
                if (pattern.matcher(uri).matches()) {
                    return Mono.just(entry.getValue());
                }
            }
        }
        throw new MockNotFound("Mock not found for " + apiKey);
    }

    private void addConfigToRegistries(MockApiConfig config){
        config.validate();
        if(config.isPathMatchingRequired()){
            Pattern compiledPattern = Pattern.compile(config.getPathMatchingRegex());
            if(pathMatchingRegistry.containsKey(compiledPattern)){
                log.warn("Path already registered for regex {}, not loading endpoint: {}", config.getPathMatchingRegex(), config.getEndpoint());
            }
            else{
                pathMatchingRegistry.put(compiledPattern, config.getId());
            }
        } else {
            String key = Utils.getKeyWithMethodAndEndpoint(config.getHttpMethod() ,config.getEndpoint());
            if(directEndpointMatchingRegistry.containsKey(key)){
                log.warn("Duplicate config for endpoint: {}, ignoring this config", config.getEndpoint());
            }
            else {
                directEndpointMatchingRegistry.put(key, config.getId());
            }
        }
    }

    private void errorHandler(Throwable t){
        log.debug("Error while registering endpoint: {}", t.getMessage(),  t.getCause());
        log.error("Error occurred while loading config into registry: {}", t.getMessage());
    }

    private void loadCompleteHandler(){
        log.info("Loaded configs, path matched endpoints: {}, direct endpoints: {}", pathMatchingRegistry.size(), directEndpointMatchingRegistry.size());
    }

}

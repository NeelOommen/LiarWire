package com.tls.LiarWire.factories;

import com.tls.LiarWire.services.ResponseProcessor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ResponseProcessorFactory {

    private final Map<String, ResponseProcessor> processorMap;

    public ResponseProcessorFactory(List<ResponseProcessor> processors) {
        this.processorMap = processors.stream()
                .collect(Collectors.toMap(ResponseProcessor::getType, Function.identity()));
    }

    public ResponseProcessor getProcessor(String type) {
        return processorMap.get(type);
    }

}

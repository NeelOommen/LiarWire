package com.tls.LiarWire.factories;

import com.tls.LiarWire.services.DelayService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DelayServiceFactory {

    private final Map<String, DelayService> delayServiceMap;

    public DelayServiceFactory(List<DelayService> processors) {
        this.delayServiceMap = processors.stream()
                .collect(Collectors.toMap(DelayService::getType, Function.identity()));
    }

    public DelayService getDelayService(String type) {
        return delayServiceMap.get(type);
    }

}

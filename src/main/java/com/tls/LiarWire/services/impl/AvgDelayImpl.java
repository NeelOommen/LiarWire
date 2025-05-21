package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.entity.MockApiConfig;
import com.tls.LiarWire.services.DelayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvgDelayImpl extends DelayService {
    @Override
    public long getDelayinMillisBasedOnPercentile(MockApiConfig config, double percentile) {
        if(null != config.getDelay()){
            return config.getDelay().getAvg();
        }
        else {
            return 0;
        }
    }

    @Override
    public String getType() {
        return "avg";
    }
}

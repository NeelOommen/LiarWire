package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.entity.MockApiConfig;
import com.tls.LiarWire.services.DelayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StepDelayImpl extends DelayService {

    @Override
    public long getDelayinMillisBasedOnPercentile(MockApiConfig config, double percentile) {
        if(null != config.getDelay()){
            if(percentile >= 99){
                return config.getDelay().getP99();
            }
            else if(percentile < 99 && percentile >= 95){
                return config.getDelay().getP95();
            }
            else if(percentile < 95 && percentile >= 90){
                return config.getDelay().getP90();
            }
            else{
                return config.getDelay().getAvg();
            }
        }
        else{
            return 0;
        }
    }

    public String getType(){
        return "step";
    }

}

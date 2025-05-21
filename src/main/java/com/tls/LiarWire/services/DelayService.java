package com.tls.LiarWire.services;

import com.tls.LiarWire.entity.MockApiConfig;

import java.util.Random;

public abstract class DelayService {

    public void delayResponse(MockApiConfig config) throws InterruptedException {
        double delayPercentile = getRandomPercentage();
        Thread.sleep(getDelayinMillisBasedOnPercentile(config, delayPercentile));
    }

    public double getRandomPercentage(){
        Random random = new Random();
        return random.nextInt(101);
    }

    public abstract long getDelayinMillisBasedOnPercentile(MockApiConfig config, double percentile);
    public abstract String getType();

}

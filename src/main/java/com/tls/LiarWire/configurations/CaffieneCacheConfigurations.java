package com.tls.LiarWire.configurations;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.tls.LiarWire.constants.StringConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class CaffieneCacheConfigurations {

    @Value("${mock.config.cache.max.size:1000}")
    private long mockConfigCacheMaxSize;

    @Value("${mock.config.cache.expiry.in.seconds:600}")
    private long mockConfigExpiryInSeconds;

    @Bean
    public Caffeine<Object, Object> mockConfigCache(){
        return Caffeine.newBuilder()
                .maximumSize(mockConfigCacheMaxSize)
                .expireAfterAccess(Duration.ofSeconds(5));
    }

    @Bean
    public CaffeineCacheManager cacheManager(Caffeine<Object, Object> caffeine){
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(StringConstants.CacheNames.MOCK_CONFIG_CACHE_NAME);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }

}

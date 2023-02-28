package com.example.authgateway.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class LocalCacheConfig {

    @Bean
    public Map<String, Set<String>> cacheMap(){
        return new HashMap<>();
    }
}

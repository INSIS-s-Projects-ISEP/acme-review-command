package com.isep.acme.config;

import java.util.UUID;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public String instanceId(){
        return UUID.randomUUID().toString();
    }
}

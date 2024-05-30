package com.solo.toycommentservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebMvcConfig {

    @LoadBalanced
    public WebClient.Builder WebClientBuilder() {

        return WebClient.builder();
    }
}
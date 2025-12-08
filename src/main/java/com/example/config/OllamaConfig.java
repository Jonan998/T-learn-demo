package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OllamaConfig {

    @Bean
    public WebClient ollamaWebClient() {
        return WebClient.builder()
                .baseUrl("http://host.docker.internal:11434")
                .build();
    }
}

package ru.teducation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MistralConfig {

  @Bean
  public WebClient mistralWebClient(@Value("${mistral.api.key}") String apiKey) {

    return WebClient.builder()
        .baseUrl("https://api.mistral.ai")
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}

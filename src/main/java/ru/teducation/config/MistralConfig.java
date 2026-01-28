package ru.teducation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class MistralConfig {

  private final AppProperties properties;

  public MistralConfig(AppProperties properties) {
    this.properties = properties;
  }

  protected String api() {
    return properties.getMistral().getApi();
  }

  protected String url() {
    return properties.getMistral().getUrl();
  }

  @Bean
  public RestClient mistralRestClient() {

    return RestClient.builder()
        .baseUrl(url())
        .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + api())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build();
  }
}

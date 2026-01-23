package ru.teducation.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private Jwt jwt = new Jwt();

  private RateLimit rateLimit = new RateLimit();

  private Mistral mistral = new Mistral();

  @Getter
  @Setter
  public static class Jwt {
    private String secret;
    private long expirationMillis;
  }

  @Getter
  @Setter
  public static class RateLimit {
    private int retryAfterSeconds = 10;
    private String message = "Слишком много запросов";
  }

  @Getter
  @Setter
  public static class Mistral {
    private String api;
    private String url;
  }
}

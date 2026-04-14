package ru.teducation;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TlLeanAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(TlLeanAppApplication.class, args);
  }

  @PostConstruct
    public void init() {
      TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
}

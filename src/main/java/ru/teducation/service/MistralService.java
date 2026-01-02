package ru.teducation.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class MistralService {

  private static final String MODEL = "mistral-small";
  private static final String API = "/v1/chat/completions";

  private final WebClient mistralWebClient;

  public List<String> generateExampleSentences(List<String> words) {

    String prompt =
        """
                For each word, write ONE short English sentence.

                Rules:
                - Sentence must contain the word.
                - Use simple daily English.
                - No explanations.
                - One sentence per line.
                - No numbering.

                Words:
                %s
                """
            .formatted(String.join(", ", words));

    long start = System.currentTimeMillis();

    String rawResponse =
        mistralWebClient
            .post()
            .uri(API)
            .bodyValue(
                Map.of(
                    "model", MODEL, "messages", List.of(Map.of("role", "user", "content", prompt))))
            .retrieve()
            .bodyToMono(JsonNode.class)
            .map(json -> json.path("choices").get(0).path("message").path("content").asText())
            .timeout(Duration.ofSeconds(6))
            .doOnError(e -> log.error("Mistral API error", e))
            .onErrorReturn("")
            .block();

    log.info(
        "Mistral сгенерировал {} слова за {} мс", words.size(), System.currentTimeMillis() - start);

    return parseSentences(rawResponse);
  }

  private List<String> parseSentences(String text) {
    if (text == null || text.isBlank()) {
      return List.of();
    }

    return Arrays.stream(text.split("\\r?\\n|(?<=\\.)\\s+"))
        .map(String::trim)
        .filter(s -> s.length() > 3)
        .toList();
  }
}

package ru.teducation.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class MistralService {

  private static final String MODEL = "mistral-small";
  private static final String API = "/v1/chat/completions";

  private final RestClient mistralRestClient;
  private final PromptProvider promptProvider;

  public List<String> generateExampleSentences(List<String> words) {

    String prompt;
    try {
      prompt = promptProvider.exampleSentencesPrompt(String.join(", ", words));
    } catch (IOException e) {
      log.warn("Не удалось загрузить промпт", e);
      return List.of();
    }

    long start = System.currentTimeMillis();

    JsonNode rawResponse =
        mistralRestClient
            .post()
            .uri(API)
            .body(
                Map.of(
                    "model", MODEL, "messages", List.of(Map.of("role", "user", "content", prompt))))
            .retrieve()
            .body(JsonNode.class);

    String content =
        Optional.ofNullable(rawResponse)
            .map(r -> r.path("choices"))
            .filter(JsonNode::isArray)
            .filter(c -> !c.isEmpty())
            .map(c -> c.get(0).path("message").path("content").asText(""))
            .orElse("");

    log.info(
        "Mistral сгенерировал {} слова за {} мс", words.size(), System.currentTimeMillis() - start);

    return parseSentences(content);
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

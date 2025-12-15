package ru.teducation.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OllamaService {

  private static final String MODEL = "phi";
  private static final String API = "/api/generate";

  private final WebClient ollamaWebClient;

  public List<String> generateExampleSentencesBatch(List<String> words) {

    String prompt =
        """
            For each word, write ONE short English sentence.
            Rules:
            - Sentence must contain the word.
            - Do NOT write definitions.
            - Do NOT explain anything.
            - Do NOT write the word alone.
            - Use simple daily English.

            Write each sentence on a new line.

            Words:
            %s
        """
            .formatted(String.join(", ", words));

    String rawResponse =
        ollamaWebClient
            .post()
            .uri(API)
            .bodyValue(
                Map.of(
                    "model",
                    MODEL,
                    "prompt",
                    prompt,
                    "stream",
                    false,
                    "options",
                    Map.of("temperature", 0.2, "num_predict", 300)))
            .retrieve()
            .bodyToMono(JsonNode.class)
            .map(json -> json.get("response").asText())
            .timeout(Duration.ofSeconds(20))
            .onErrorReturn("")
            .block();

    return parseSentences(rawResponse);
  }

  private List<String> parseSentences(String text) {
    if (text == null || text.isBlank()) {
      return List.of();
    }

    return Arrays.stream(text.split("\n"))
        .map(String::trim)
        .map(line -> line.replaceFirst("^[\\d\\.-]*\\s*", ""))
        .filter(line -> !line.isEmpty())
        .toList();
  }
}

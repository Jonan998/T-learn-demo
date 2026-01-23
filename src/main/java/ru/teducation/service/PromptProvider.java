package ru.teducation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class PromptProvider {

    private final ResourceLoader resourceLoader;

    public String exampleSentencesPrompt(String words) throws IOException {
        Resource resource = resourceLoader.getResource(
                "classpath:prompts/example_sentences.txt");
        return new String(resource.getInputStream().readAllBytes())
                .formatted(words);
    }
}


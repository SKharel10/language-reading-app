package com.languageapp.backend.service;

import com.languageapp.backend.dto.request.TranslationRequestDto;
import com.languageapp.backend.dto.response.TranslationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final RestClient restClient;

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    private String buildPrompt(TranslationRequestDto request) {
        return """
                Translate the following %s text into English using the surrounding context to gather meaning.
                Reply with only the translation, nothing else.
                Context: %s,
                Text to translate: %s
                """.formatted(request.sourceLanguage(), request.context(), request.text());
    }

    private GeminiRequest buildRequestBody(String prompt) {
        Part part = new Part(prompt);
        Content content = new Content(List.of(part));
        return new GeminiRequest(List.of(content));
    }

    private GeminiResponse callGemini(GeminiRequest requestBody) {
        return restClient.post()
                .uri("/v1beta/models/{model}:generateContent?key={apiKey}", model, apiKey)
                .body(requestBody)
                .retrieve()
                .body(GeminiResponse.class);
    }



    public TranslationResponseDto translate(TranslationRequestDto request) {
        String prompt = buildPrompt(request);
        GeminiRequest requestBody = buildRequestBody(prompt);
        GeminiResponse response = null;

        try {
            response = callGemini(requestBody);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Translation service unavailable...", e);
        }


        if (response == null || response.candidates() == null || response.candidates().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No translation received");
        }

        String translation = response.candidates().getFirst().content().parts().getFirst().text().trim();
        return new TranslationResponseDto(translation);
    }


    private record GeminiRequest(List<Content> contents) {}
    private record GeminiResponse(List<Candidate> candidates) {}
    private record Candidate(Content content) {}
    private record Content(List<Part> parts) {}
    private record Part(String text) {}
}

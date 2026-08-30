package com.languageapp.backend.dto.request;

import com.languageapp.backend.model.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TranslationRequestDto(
        @NotNull Language sourceLanguage,
        @NotBlank String text,
        String context
) {
}

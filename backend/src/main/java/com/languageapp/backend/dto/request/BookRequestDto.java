package com.languageapp.backend.dto.request;

import com.languageapp.backend.model.CEFRLevel;
import com.languageapp.backend.model.Language;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record BookRequestDto(
    String title,
    String description,
    Language language,
    CEFRLevel level,
    String coverImageUrl,
    @NotEmpty List<@Valid ChapterRequestDto> chapters) {}

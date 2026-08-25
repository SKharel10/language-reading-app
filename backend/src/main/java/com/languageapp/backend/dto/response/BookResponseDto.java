package com.languageapp.backend.dto.response;

import com.languageapp.backend.model.CEFRLevel;
import com.languageapp.backend.model.Language;
import java.util.List;
import java.util.UUID;

public record BookResponseDto(
    UUID id,
    String title,
    String description,
    Language language,
    CEFRLevel level,
    String coverImageUrl,
    List<ChapterResponseDto> chapters) {}

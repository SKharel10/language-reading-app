package com.languageapp.backend.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReadingProgressRequestDto(UUID bookId, @NotNull UUID pageId) {}

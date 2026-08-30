package com.languageapp.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ReadingProgressRequestDto(@NotNull UUID pageId) {}

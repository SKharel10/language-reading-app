package com.languageapp.backend.dto.request;

import java.util.UUID;

public record ReadingProgressRequestDto(
        UUID bookId,
        UUID pageId
) {}

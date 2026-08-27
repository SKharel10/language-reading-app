package com.languageapp.backend.dto.response;

import java.util.UUID;

public record ReadingProgressResponseDto(UUID id, UUID bookId, UUID pageId) {}

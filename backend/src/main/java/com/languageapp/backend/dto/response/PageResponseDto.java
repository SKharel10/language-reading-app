package com.languageapp.backend.dto.response;

import java.util.UUID;

public record PageResponseDto(UUID id, Integer number, String content) {}

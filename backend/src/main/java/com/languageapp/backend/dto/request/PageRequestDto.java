package com.languageapp.backend.dto.request;

import java.util.UUID;

public record PageRequestDto(UUID id, Integer number, String content) {}

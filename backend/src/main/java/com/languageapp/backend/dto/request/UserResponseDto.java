package com.languageapp.backend.dto.request;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name
) {
}

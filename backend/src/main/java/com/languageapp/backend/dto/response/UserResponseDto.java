package com.languageapp.backend.dto.response;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name
) {
}

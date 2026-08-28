package com.languageapp.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDto(@NotBlank @Size(min = 2, max = 70) String name) {}

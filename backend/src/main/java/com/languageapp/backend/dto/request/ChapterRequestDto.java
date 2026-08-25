package com.languageapp.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ChapterRequestDto(List<PageRequestDto> pages, String name, @NotNull Integer number) {}

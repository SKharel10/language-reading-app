package com.languageapp.backend.dto.response;

import java.util.List;

public record ChapterResponseDto(List<PageResponseDto> pages, String name, Integer number) {}

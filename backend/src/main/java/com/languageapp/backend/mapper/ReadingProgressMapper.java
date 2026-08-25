package com.languageapp.backend.mapper;

import com.languageapp.backend.dto.request.ReadingProgressRequestDto;
import com.languageapp.backend.dto.response.ReadingProgressResponseDto;
import com.languageapp.backend.model.ReadingProgress;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReadingProgressMapper {
    ReadingProgress toEntity(ReadingProgressRequestDto request);
    ReadingProgressResponseDto toDto(ReadingProgress readingProgress);
}

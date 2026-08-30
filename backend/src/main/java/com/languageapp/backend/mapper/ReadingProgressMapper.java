package com.languageapp.backend.mapper;

import com.languageapp.backend.dto.request.ReadingProgressRequestDto;
import com.languageapp.backend.dto.response.ReadingProgressResponseDto;
import com.languageapp.backend.model.ReadingProgress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReadingProgressMapper {
  ReadingProgress toEntity(ReadingProgressRequestDto request);

  @Mapping(source = "book.id", target = "bookId")
  @Mapping(source = "page.id", target = "pageId")
  ReadingProgressResponseDto toDto(ReadingProgress readingProgress);
}

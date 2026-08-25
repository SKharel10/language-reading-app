package com.languageapp.backend.mapper;

import com.languageapp.backend.dto.request.BookRequestDto;
import com.languageapp.backend.dto.request.ChapterRequestDto;
import com.languageapp.backend.dto.request.PageRequestDto;
import com.languageapp.backend.dto.response.BookResponseDto;
import com.languageapp.backend.dto.response.ChapterResponseDto;
import com.languageapp.backend.dto.response.PageResponseDto;
import com.languageapp.backend.model.Book;
import com.languageapp.backend.model.Chapter;
import com.languageapp.backend.model.Page;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

  @Mapping(target = "id", ignore = true)
  Book toEntity(BookRequestDto request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "book", ignore = true)
  Chapter toEntity(ChapterRequestDto request);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "chapter", ignore = true)
  Page toEntity(PageRequestDto request);

  BookResponseDto toDto(Book book);

  ChapterResponseDto toDto(Chapter chapter);

  PageResponseDto toDto(Page page);
}

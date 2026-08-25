package com.languageapp.backend.service;

import com.languageapp.backend.dto.request.BookRequestDto;
import com.languageapp.backend.dto.response.BookResponseDto;
import com.languageapp.backend.mapper.BookMapper;
import com.languageapp.backend.model.Book;
import com.languageapp.backend.model.Chapter;
import com.languageapp.backend.model.Page;
import com.languageapp.backend.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;

  public BookResponseDto saveBook(BookRequestDto request) {
    Book book = bookMapper.toEntity(request);

    for (Chapter chapter : book.getChapters()) {
      chapter.setBook(book);

      for (Page page : chapter.getPages()) {
        page.setChapter(chapter);
      }
    }

    Book savedBook = bookRepository.save(book);
    return bookMapper.toDto(savedBook);
  }

  public List<BookResponseDto> getAllBooks() {
    return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
  }

  public Optional<BookResponseDto> getBookById(UUID id) {
    return bookRepository.findById(id).map(bookMapper::toDto);
  }

  public void deleteBookById(UUID id) {
    bookRepository.deleteById(id);
  }
}

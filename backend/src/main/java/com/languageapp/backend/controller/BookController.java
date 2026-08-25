package com.languageapp.backend.controller;

import com.languageapp.backend.dto.request.BookRequestDto;
import com.languageapp.backend.dto.response.BookResponseDto;
import com.languageapp.backend.service.BookService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
  private final BookService bookService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponseDto createBook(@Valid @RequestBody BookRequestDto book) {
    return bookService.saveBook(book);
  }

  @GetMapping
  public List<BookResponseDto> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookResponseDto> getBook(@PathVariable UUID id) {
    BookResponseDto bookResponseDto = bookService.getBookById(id).orElse(null);

    if (bookResponseDto == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(bookResponseDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteBookById(@PathVariable UUID id) {
    bookService.deleteBookById(id);
  }
}

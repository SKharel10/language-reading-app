package com.languageapp.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.languageapp.backend.dto.request.BookRequestDto;
import com.languageapp.backend.dto.response.BookResponseDto;
import com.languageapp.backend.model.CEFRLevel;
import com.languageapp.backend.model.Language;
import com.languageapp.backend.service.BookService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BookController.class)
class BookControllerTests {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private BookService bookService;

  @Test
  public void createBook_WithValidRequest_Returns201() throws Exception {

    UUID id = UUID.randomUUID();
    BookResponseDto expected =
        new BookResponseDto(
            id,
            "Book title",
            "Book description",
            Language.ENGLISH,
            CEFRLevel.B1,
            "test.jpg",
            List.of());

    when(bookService.saveBook(any(BookRequestDto.class))).thenReturn(expected);

    mockMvc
        .perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                            {
                                              "title": "Book title",
                                              "description": "Book description",
                                              "language": "ENGLISH",
                                              "level": "B1",
                                              "coverImageUrl": "...",
                                              "chapters": [
                                                {
                                                  "pages": [
                                                    {"number": "1", "content": "First page here"},
                                                    {"number": "2", "content": "Second page here"},
                                                    {"number": "3", "content": "Third page here"}
                                                  ],
                                                  "name": "Chapter 1",
                                                  "number": "1"
                                                }
                                              ]
                                            }"""))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.title").value("Book title"))
        .andExpect(jsonPath("$.language").value("ENGLISH"))
        .andExpect(jsonPath("$.level").value("B1"));
  }

  @Test
  public void createBook_WithInvalidRequest_Returns400() throws Exception {
    mockMvc
        .perform(
            post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                    {
                                                          "title": "",
                                                          "description": "Book description",
                                                          "language": "ENGLISH",
                                                          "level": "B1",
                                                          "coverImageUrl": "test.jpg",
                                                          "chapters": []
                                                        }

                                    """))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getAllBooks_returnsBooks() throws Exception {
    UUID id = UUID.randomUUID();

    BookResponseDto book =
        new BookResponseDto(
            id,
            "Book title",
            "Book description",
            Language.ENGLISH,
            CEFRLevel.B1,
            "test.jpg",
            List.of());

    when(bookService.getAllBooks()).thenReturn(List.of(book));

    mockMvc
        .perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(id.toString()))
        .andExpect(jsonPath("$[0].title").value("Book title"))
        .andExpect(jsonPath("$[0].language").value("ENGLISH"))
        .andExpect(jsonPath("$[0].level").value("B1"));
  }

  @Test
  void getAllBooks_whenNoBooksExist_returnsEmptyList() throws Exception {

    when(bookService.getAllBooks()).thenReturn(List.of());

    mockMvc
        .perform(get("/api/books"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  void getBook_withExistingId_returnsBook() throws Exception {

    UUID id = UUID.randomUUID();

    BookResponseDto book =
        new BookResponseDto(
            id,
            "Book title",
            "Book description",
            Language.ENGLISH,
            CEFRLevel.B1,
            "test.jpg",
            List.of());

    when(bookService.getBookById(id)).thenReturn(java.util.Optional.of(book));

    mockMvc
        .perform(get("/api/books/{id}", id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id.toString()))
        .andExpect(jsonPath("$.title").value("Book title"))
        .andExpect(jsonPath("$.language").value("ENGLISH"))
        .andExpect(jsonPath("$.level").value("B1"));
  }

  @Test
  void getBook_withInvalidId_returns400() throws Exception {
    mockMvc.perform(get("/api/books/not-a-uuid")).andExpect(status().isBadRequest());
  }

  @Test
  void getBook_withNonExistingId_returns404() throws Exception {

    UUID id = UUID.randomUUID();

    when(bookService.getBookById(id)).thenReturn(java.util.Optional.empty());

    mockMvc.perform(get("/api/books/{id}", id)).andExpect(status().isNotFound());
  }

  @Test
  void deleteBook_withExistingId_returns204() throws Exception {

    UUID id = UUID.randomUUID();

    doNothing().when(bookService).deleteBookById(id);

    mockMvc.perform(delete("/api/books/{id}", id)).andExpect(status().isNoContent());
  }
}

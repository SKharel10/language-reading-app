package com.languageapp.backend.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.languageapp.backend.config.SecurityConfig;
import com.languageapp.backend.dto.request.ReadingProgressRequestDto;
import com.languageapp.backend.dto.response.ReadingProgressResponseDto;
import com.languageapp.backend.model.User;
import com.languageapp.backend.service.AuthenticatedUserService;
import com.languageapp.backend.service.ReadingProgressService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Import(SecurityConfig.class)
@WebMvcTest(ReadingProgressController.class)
public class ReadingProgressControllerTests {

  @Autowired private MockMvc mockMvc;
  @MockitoBean private ReadingProgressService readingProgressService;
  @MockitoBean private AuthenticatedUserService authenticatedUserService;

  @Test
  void createReadingProgress_WithValidRequest_Returns201() throws Exception {
    UUID userId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();
    UUID pageId = UUID.randomUUID();
    UUID progressId = UUID.randomUUID();

    ReadingProgressResponseDto expected =
        new ReadingProgressResponseDto(progressId, bookId, pageId);

    when(authenticatedUserService.getOrCreateUser(any(Jwt.class)))
        .thenReturn(new User(userId, "auth0|test-user", "Test User", List.of()));
    when(readingProgressService.createBookReadingProgress(
            eq(userId), eq(bookId), any(ReadingProgressRequestDto.class)))
        .thenReturn(Optional.of(expected));

    mockMvc
        .perform(
            post("/api/books/{bookId}/progress", bookId)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                {
                                  "pageId": "%s"
                                }
                                """
                        .formatted(pageId)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value(progressId.toString()))
        .andExpect(jsonPath("$.bookId").value(bookId.toString()))
        .andExpect(jsonPath("$.pageId").value(pageId.toString()));
  }

  @Test
  void createReadingProgress_WithDuplicateRequest_Returns409() throws Exception {
    UUID userId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();
    UUID pageId = UUID.randomUUID();

    when(authenticatedUserService.getOrCreateUser(any(Jwt.class)))
        .thenReturn(new User(userId, "auth0|test-user", "Test User", List.of()));
    when(readingProgressService.createBookReadingProgress(
            eq(userId), eq(bookId), any(ReadingProgressRequestDto.class)))
        .thenReturn(Optional.empty());

    mockMvc
        .perform(
            post("/api/books/{bookId}/progress", bookId)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                                        {
                                        "pageId": "%s"
                                        }


                                        """
                        .formatted(pageId)))
        .andExpect(status().isConflict());
  }

  @Test
  void createReadingProgress_WithInvalidRequest_Returns400() throws Exception {

    UUID bookId = UUID.randomUUID();

    mockMvc
        .perform(
            post("/api/books/{bookId}/progress", bookId)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "pageId": null
                            }
                            """))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getReadingProgress_WithExistingProgress_Returns200() throws Exception {

    UUID userId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();
    UUID pageId = UUID.randomUUID();
    UUID progressId = UUID.randomUUID();

    ReadingProgressResponseDto expected =
        new ReadingProgressResponseDto(progressId, bookId, pageId);

    when(authenticatedUserService.getOrCreateUser(any(Jwt.class)))
        .thenReturn(new User(userId, "auth0|test-user", "Test User", List.of()));
    when(readingProgressService.getBookReadingProgress(userId, bookId))
        .thenReturn(Optional.of(expected));

    mockMvc
        .perform(get("/api/books/{bookId}/progress", bookId).with(jwt()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(progressId.toString()))
        .andExpect(jsonPath("$.bookId").value(bookId.toString()))
        .andExpect(jsonPath("$.pageId").value(pageId.toString()));
  }

  @Test
  void getReadingProgress_WithNonexistentProgress_Returns404() throws Exception {

    UUID userId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();

    when(authenticatedUserService.getOrCreateUser(any(Jwt.class)))
        .thenReturn(new User(userId, "auth0|test-user", "Test User", List.of()));
    when(readingProgressService.getBookReadingProgress(userId, bookId))
        .thenReturn(Optional.empty());

    mockMvc
        .perform(get("/api/books/{bookId}/progress", bookId).with(jwt()))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateReadingProgress_WithExistingProgress_Returns200() throws Exception {

    UUID userId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();
    UUID pageId = UUID.randomUUID();
    UUID progressId = UUID.randomUUID();

    ReadingProgressResponseDto expected =
        new ReadingProgressResponseDto(progressId, bookId, pageId);

    when(authenticatedUserService.getOrCreateUser(any(Jwt.class)))
        .thenReturn(new User(userId, "auth0|test-user", "Test User", List.of()));
    when(readingProgressService.updateBookReadingProgress(
            eq(userId), eq(bookId), any(ReadingProgressRequestDto.class)))
        .thenReturn(Optional.of(expected));

    mockMvc
        .perform(
            put("/api/books/{bookId}/progress", bookId)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "pageId": "%s"
                            }
                            """
                        .formatted(pageId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(progressId.toString()))
        .andExpect(jsonPath("$.bookId").value(bookId.toString()))
        .andExpect(jsonPath("$.pageId").value(pageId.toString()));
  }

  @Test
  void updateReadingProgress_WithNonexistentProgress_Returns404() throws Exception {

    UUID userId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();
    UUID pageId = UUID.randomUUID();

    when(authenticatedUserService.getOrCreateUser(any(Jwt.class)))
        .thenReturn(new User(userId, "auth0|test-user", "Test User", List.of()));
    when(readingProgressService.updateBookReadingProgress(
            eq(userId), eq(bookId), any(ReadingProgressRequestDto.class)))
        .thenReturn(Optional.empty());

    mockMvc
        .perform(
            put("/api/books/{bookId}/progress", bookId)
                .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                            {
                              "pageId": "%s"
                            }
                            """
                        .formatted(pageId)))
        .andExpect(status().isNotFound());
  }

  @Test
  void deleteReadingProgress_WithExistingProgress_Returns204() throws Exception {

    UUID userId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();

    when(authenticatedUserService.getOrCreateUser(any(Jwt.class)))
        .thenReturn(new User(userId, "auth0|test-user", "Test User", List.of()));
    when(readingProgressService.deleteReadingProgress(userId, bookId)).thenReturn(true);

    mockMvc
        .perform(delete("/api/books/{bookId}/progress", bookId).with(jwt()))
        .andExpect(status().isNoContent());
  }

  @Test
  void deleteReadingProgress_WithNonexistentProgress_Returns404() throws Exception {

    UUID userId = UUID.randomUUID();
    UUID bookId = UUID.randomUUID();

    when(authenticatedUserService.getOrCreateUser(any(Jwt.class)))
        .thenReturn(new User(userId, "auth0|test-user", "Test User", List.of()));
    when(readingProgressService.deleteReadingProgress(userId, bookId)).thenReturn(false);

    mockMvc
        .perform(delete("/api/books/{bookId}/progress", bookId).with(jwt()))
        .andExpect(status().isNotFound());
  }
}

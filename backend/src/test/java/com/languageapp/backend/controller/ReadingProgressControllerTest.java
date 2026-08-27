package com.languageapp.backend.controller;

import com.languageapp.backend.dto.request.ReadingProgressRequestDto;
import com.languageapp.backend.dto.response.ReadingProgressResponseDto;
import com.languageapp.backend.service.ReadingProgressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReadingProgressController.class)
public class ReadingProgressControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockitoBean private ReadingProgressService readingProgressService;

    @Test
    void createReadingProgress_WithValidRequest_Returns201() throws Exception {
        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        UUID pageId = UUID.randomUUID();
        UUID progressId = UUID.randomUUID();

        ReadingProgressResponseDto expected = new ReadingProgressResponseDto(
                progressId,
                bookId,
                pageId
        );

        when(readingProgressService.createBookReadingProgress(eq(userId), eq(bookId), any(ReadingProgressRequestDto.class))).thenReturn(Optional.of(expected));

        mockMvc.perform(
                post("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "pageId": "%s"
                                }
                                """.formatted(pageId))
        )

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

        when (readingProgressService.createBookReadingProgress(eq(userId), eq(bookId), any(ReadingProgressRequestDto.class))).thenReturn(Optional.empty());


        mockMvc.perform(
                post("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                        "pageId": "%s"
                                        }
                                        
                                        
                                        """.formatted(pageId)
                        ))
                .andExpect(status().isConflict());
    }

    @Test
    void createReadingProgress_WithInvalidRequest_Returns400() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        mockMvc.perform(
                        post("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "pageId": null
                            }
                            """)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void getReadingProgress_WithExistingProgress_Returns200() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        UUID pageId = UUID.randomUUID();
        UUID progressId = UUID.randomUUID();

        ReadingProgressResponseDto expected =
                new ReadingProgressResponseDto(
                        progressId,
                        bookId,
                        pageId
                );

        when(readingProgressService.getBookReadingProgress(userId, bookId))
                .thenReturn(Optional.of(expected));

        mockMvc.perform(
                        get("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(progressId.toString()))
                .andExpect(jsonPath("$.bookId").value(bookId.toString()))
                .andExpect(jsonPath("$.pageId").value(pageId.toString()));
    }


    @Test
    void getReadingProgress_WithNonexistentProgress_Returns404() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        when(readingProgressService.getBookReadingProgress(userId, bookId))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        get("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void updateReadingProgress_WithExistingProgress_Returns200() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();
        UUID pageId = UUID.randomUUID();
        UUID progressId = UUID.randomUUID();

        ReadingProgressResponseDto expected =
                new ReadingProgressResponseDto(
                        progressId,
                        bookId,
                        pageId
                );

        when(readingProgressService.updateBookReadingProgress(
                eq(userId),
                eq(bookId),
                any(ReadingProgressRequestDto.class)
        )).thenReturn(Optional.of(expected));

        mockMvc.perform(
                        put("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "pageId": "%s"
                            }
                            """.formatted(pageId))
                )
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

        when(readingProgressService.updateBookReadingProgress(
                eq(userId),
                eq(bookId),
                any(ReadingProgressRequestDto.class)
        )).thenReturn(Optional.empty());

        mockMvc.perform(
                        put("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                            {
                              "pageId": "%s"
                            }
                            """.formatted(pageId))
                )
                .andExpect(status().isNotFound());
    }


    @Test
    void deleteReadingProgress_WithExistingProgress_Returns204() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        when(readingProgressService.deleteReadingProgress(userId, bookId))
                .thenReturn(true);

        mockMvc.perform(
                        delete("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                )
                .andExpect(status().isNoContent());
    }


    @Test
    void deleteReadingProgress_WithNonexistentProgress_Returns404() throws Exception {

        UUID userId = UUID.randomUUID();
        UUID bookId = UUID.randomUUID();

        when(readingProgressService.deleteReadingProgress(userId, bookId))
                .thenReturn(false);

        mockMvc.perform(
                        delete("/api/users/{userId}/books/{bookId}/progress", userId, bookId)
                )
                .andExpect(status().isNotFound());
    }

}

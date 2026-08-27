package com.languageapp.backend.controller;

import com.languageapp.backend.dto.request.ReadingProgressRequestDto;
import com.languageapp.backend.dto.response.ReadingProgressResponseDto;
import com.languageapp.backend.service.ReadingProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReadingProgressController {
    private final ReadingProgressService readingProgressService;

    @PostMapping("/users/{userId}/books/{bookId}/progress")
    public ResponseEntity<ReadingProgressResponseDto> createReadingProgress(@PathVariable UUID userId,
                                                                            @PathVariable UUID bookId,
                                                                            @Valid @RequestBody ReadingProgressRequestDto request) {

        Optional<ReadingProgressResponseDto> response = readingProgressService.createBookReadingProgress(userId, bookId, request);

        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
    }


    @GetMapping("/users/{userId}/books/{bookId}/progress")
    public ResponseEntity<ReadingProgressResponseDto> getReadingProgress(
            @PathVariable UUID userId,
            @PathVariable UUID bookId) {

        Optional<ReadingProgressResponseDto> readingProgress = readingProgressService.getBookReadingProgress(userId, bookId);

        if (readingProgress.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(readingProgress.get());
    }


    @PutMapping("/users/{userId}/books/{bookId}/progress")
    public ResponseEntity<ReadingProgressResponseDto> updateReadingProgress(
            @PathVariable UUID userId,
            @PathVariable UUID bookId,
            @Valid @RequestBody ReadingProgressRequestDto request) {

        Optional<ReadingProgressResponseDto> response = readingProgressService.updateBookReadingProgress(userId, bookId, request);
        if (response.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(response.get());
    }

    @DeleteMapping("/users/{userId}/books/{bookId}/progress")
    public ResponseEntity<Void> deleteReadingProgress(
            @PathVariable UUID userId,
            @PathVariable UUID bookId) {
        boolean status = readingProgressService.deleteReadingProgress(userId, bookId);

        if (status) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }



}

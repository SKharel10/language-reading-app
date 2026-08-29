package com.languageapp.backend.controller;

import com.languageapp.backend.dto.request.ReadingProgressRequestDto;
import com.languageapp.backend.dto.response.ReadingProgressResponseDto;
import com.languageapp.backend.model.User;
import com.languageapp.backend.service.AuthenticatedUserService;
import com.languageapp.backend.service.ReadingProgressService;
import jakarta.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReadingProgressController {
  private final ReadingProgressService readingProgressService;
  private final AuthenticatedUserService authenticatedUserService;

  @PostMapping("/books/{bookId}/progress")
  public ResponseEntity<ReadingProgressResponseDto> createReadingProgress(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable UUID bookId,
      @Valid @RequestBody ReadingProgressRequestDto request) {

    User user = authenticatedUserService.getOrCreateUser(jwt);

    Optional<ReadingProgressResponseDto> response =
        readingProgressService.createBookReadingProgress(user.getId(), bookId, request);

    if (response.isEmpty()) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(response.get());
  }

  @GetMapping("/books/{bookId}/progress")
  public ResponseEntity<ReadingProgressResponseDto> getReadingProgress(
      @AuthenticationPrincipal Jwt jwt, @PathVariable UUID bookId) {

    User user = authenticatedUserService.getOrCreateUser(jwt);
    Optional<ReadingProgressResponseDto> readingProgress =
        readingProgressService.getBookReadingProgress(user.getId(), bookId);

    if (readingProgress.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(readingProgress.get());
  }

  @PutMapping("/books/{bookId}/progress")
  public ResponseEntity<ReadingProgressResponseDto> updateReadingProgress(
      @AuthenticationPrincipal Jwt jwt,
      @PathVariable UUID bookId,
      @Valid @RequestBody ReadingProgressRequestDto request) {

    User user = authenticatedUserService.getOrCreateUser(jwt);
    Optional<ReadingProgressResponseDto> response =
        readingProgressService.updateBookReadingProgress(user.getId(), bookId, request);
    if (response.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    return ResponseEntity.ok(response.get());
  }

  @DeleteMapping("/books/{bookId}/progress")
  public ResponseEntity<Void> deleteReadingProgress(
      @AuthenticationPrincipal Jwt jwt, @PathVariable UUID bookId) {

    User user = authenticatedUserService.getOrCreateUser(jwt);
    boolean status = readingProgressService.deleteReadingProgress(user.getId(), bookId);

    if (status) {
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}

package com.languageapp.backend.service;

import com.languageapp.backend.dto.request.ReadingProgressRequestDto;
import com.languageapp.backend.dto.response.ReadingProgressResponseDto;
import com.languageapp.backend.mapper.ReadingProgressMapper;
import com.languageapp.backend.model.Page;
import com.languageapp.backend.model.ReadingProgress;
import com.languageapp.backend.repository.BookRepository;
import com.languageapp.backend.repository.PageRepository;
import com.languageapp.backend.repository.ReadingProgressRepository;
import com.languageapp.backend.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadingProgressService {
  private final ReadingProgressMapper mapper;
  private final ReadingProgressRepository readingProgressRepository;
  private final UserRepository userRepository;
  private final BookRepository bookRepository;
  private final PageRepository pageRepository;

  public Optional<ReadingProgressResponseDto> getBookReadingProgress(UUID userId, UUID bookId) {
    return readingProgressRepository.findByUserIdAndBookId(userId, bookId).map(mapper::toDto);
  }

  public List<ReadingProgressResponseDto> getAllBookReadingProgress() {
    return readingProgressRepository.findAll().stream().map(mapper::toDto).toList();
  }

  public Optional<ReadingProgressResponseDto> createBookReadingProgress(
      UUID userId, UUID bookId, ReadingProgressRequestDto request) {
    if (readingProgressRepository.findByUserIdAndBookId(userId, bookId).isPresent()) {
      return Optional.empty();
    }
    ReadingProgress readingProgress = mapper.toEntity(request);

    readingProgress.setUser(
        userRepository.findById(userId).orElseThrow(IllegalStateException::new));
    readingProgress.setBook(
        bookRepository.findById(bookId).orElseThrow(IllegalStateException::new));

    Optional<Page> page = pageRepository.findById(request.pageId());
    if (page.isEmpty()) {
      return Optional.empty();
    }
    readingProgress.setPage(page.get());
    return Optional.ofNullable(mapper.toDto(readingProgressRepository.save(readingProgress)));
  }

  public Optional<ReadingProgressResponseDto> updateBookReadingProgress(
      UUID userId, UUID bookId, ReadingProgressRequestDto request) {
    Optional<ReadingProgress> readingProgress =
        readingProgressRepository.findByUserIdAndBookId(userId, bookId);

    if (readingProgress.isEmpty()) {
      return Optional.empty();
    }

    Optional<Page> page = pageRepository.findById(request.pageId());

    if (page.isEmpty()) {
      return Optional.empty();
    }

    readingProgress.get().setPage(page.get());
    ReadingProgress saved = readingProgressRepository.save(readingProgress.get());
    return Optional.ofNullable(mapper.toDto(saved));
  }

  @Transactional
  public boolean deleteReadingProgress(UUID userId, UUID bookId) {
    if (readingProgressRepository.existsByUserIdAndBookId(userId, bookId)) {
      readingProgressRepository.deleteByUserIdAndBookId(userId, bookId);
      return true;
    } else {
      return false;
    }
  }
}

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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<ReadingProgressResponseDto> saveBookReadingProgress(UUID userId, UUID bookId, ReadingProgressRequestDto request) {
        ReadingProgress readingProgress = readingProgressRepository.findByUserIdAndBookId(userId, bookId).orElseGet(() -> mapper.toEntity(request));
        Page page = pageRepository.findById(request.pageId()).orElse(null);

        if (page == null) {
            return Optional.empty();
        }

        if (readingProgress.getBook() == null || readingProgress.getUser() == null) {
            readingProgress.setBook(bookRepository.findById(bookId).orElseThrow(IllegalStateException::new));
            readingProgress.setUser(userRepository.findById(userId).orElseThrow(IllegalStateException::new));
        }
        readingProgress.setPage(page);
        ReadingProgress saved = readingProgressRepository.save(readingProgress);
        return Optional.ofNullable(mapper.toDto(saved));
    }

    public boolean deleteReadingProgress(UUID userId, UUID bookId) {
        if (readingProgressRepository.existsByUserIdAndBookId(userId, bookId)) {
            readingProgressRepository.deleteByUserIdAndBookId(userId, bookId);
            return true;
        } else {
            return false;
        }

    }

}

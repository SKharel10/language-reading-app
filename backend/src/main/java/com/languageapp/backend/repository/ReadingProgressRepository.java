package com.languageapp.backend.repository;

import com.languageapp.backend.model.Book;
import com.languageapp.backend.model.ReadingProgress;

import java.util.Optional;
import java.util.UUID;

import com.languageapp.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, UUID> {
    Optional<ReadingProgress> findByUserIdAndBookId(UUID userId, UUID bookId);
    boolean existsByUserIdAndBookId(UUID userId, UUID bookId);
    void deleteByUserIdAndBookId(UUID userId, UUID bookId);
}

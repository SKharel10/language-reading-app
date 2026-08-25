package com.languageapp.backend.repository;

import com.languageapp.backend.model.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, UUID> {
}

package com.languageapp.backend.repository;

import com.languageapp.backend.model.ReadingProgress;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, UUID> {}

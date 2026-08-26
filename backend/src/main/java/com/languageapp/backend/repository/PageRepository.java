package com.languageapp.backend.repository;

import com.languageapp.backend.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PageRepository extends JpaRepository<Page, UUID> {
}

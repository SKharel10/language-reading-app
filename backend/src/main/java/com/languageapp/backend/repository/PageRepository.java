package com.languageapp.backend.repository;

import com.languageapp.backend.model.Page;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<Page, UUID> {}

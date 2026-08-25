package com.languageapp.backend.repository;

import com.languageapp.backend.model.Book;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, UUID> {}

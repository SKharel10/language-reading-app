package com.languageapp.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class ReadingProgress {
  @Id private UUID id;
  @ManyToOne private User user;
  @ManyToOne private Book book;
  private Integer currentPage;
  private Integer currentChapter;

  protected ReadingProgress() {}
}

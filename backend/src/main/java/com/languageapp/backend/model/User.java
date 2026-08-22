package com.languageapp.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class User {
  @Id private UUID id;
  private String name;
  @OneToMany private List<ReadingProgress> readingProgress;

  protected User() {}
}

package com.languageapp.backend.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Book {

  @Id private UUID id;
  private String title;
  private String description;

  @Enumerated(EnumType.STRING)
  private Language language;

  @Enumerated(EnumType.STRING)
  private CEFRLevel level;

  private String coverImageUrl;
  @OneToMany private List<Chapter> chapters;

  protected Book() {}
}

package com.languageapp.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Page {
  @Id private UUID id;
  private Integer number;
  private String content;

  @ManyToOne private Chapter chapter;

  protected Page() {}
}

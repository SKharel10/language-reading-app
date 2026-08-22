package com.languageapp.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Entity
public class Chapter {
  @Id private UUID id;
  @OneToMany private List<Page> pages;
  private String name;
  private Integer number;
  @ManyToOne private Book book;

  protected Chapter() {}
}

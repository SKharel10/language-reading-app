package com.languageapp.backend.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Chapter {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Page> pages;

  private String name;
  private Integer number;
  @ManyToOne private Book book;
}

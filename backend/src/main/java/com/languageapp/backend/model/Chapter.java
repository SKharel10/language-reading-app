package com.languageapp.backend.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Chapter {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  @OneToMany(mappedBy = "chapter", cascade = CascadeType.ALL, orphanRemoval = true)
  @Setter
  private List<Page> pages;

  @Setter private String name;
  @Setter private Integer number;
  @Setter @ManyToOne private Book book;
}

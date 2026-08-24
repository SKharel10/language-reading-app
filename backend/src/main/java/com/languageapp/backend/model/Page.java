package com.languageapp.backend.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Page {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  private Integer number;
  private String content;
  @ManyToOne private Chapter chapter;
}

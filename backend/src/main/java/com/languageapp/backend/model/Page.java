package com.languageapp.backend.model;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Page {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  @Setter private Integer number;
  @Setter private String content;
  @Setter @ManyToOne private Chapter chapter;
}

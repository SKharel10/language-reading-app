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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
public class ReadingProgress {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  @JoinColumn(name = "user_id")
  @ManyToOne
  private User user;

  @JoinColumn(name = "book_id")
  @ManyToOne
  private Book book;

  @ManyToOne private Page page;
}

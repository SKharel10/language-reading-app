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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"}))
public class ReadingProgress {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  UUID id;

  @JoinColumn(name = "user_id")
  @ManyToOne
  @Setter
  private User user;

  @JoinColumn(name = "book_id")
  @Setter
  @ManyToOne
  private Book book;

  @Setter
  @ManyToOne private Page page;
}

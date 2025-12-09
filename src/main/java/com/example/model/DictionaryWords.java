package com.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dictionary_words")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryWords {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "word_id")
  private Word word;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dictionary_id")
  private Dictionary dictionary;

  public DictionaryWords(Word word, Dictionary dictionary) {
    this.dictionary = dictionary;
    this.word = word;
  }
}

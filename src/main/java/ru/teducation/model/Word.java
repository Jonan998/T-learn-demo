package ru.teducation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "words")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Word {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "eng_lang")
  private String engLang;

  @Column(name = "rus_lang")
  private String rusLang;

  @Column(name = "transcription")
  private String transcription;

  @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
  @JsonIgnore
  @Builder.Default
  private List<CardsWords> cardsWords = new ArrayList<>();

  @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  @Builder.Default
  private List<DictionaryWords> dictionaryWords = new ArrayList<>();

  public Word(String engLang, String rusLang, String transcription) {
    this.engLang = engLang;
    this.rusLang = rusLang;
    this.transcription = transcription;
  }

  public Word(String engLang, String rusLang) {
    this.engLang = engLang;
    this.rusLang = rusLang;
  }

  public Word(Integer id, String engLang, String rusLang, String transcription) {
    this.id = id;
    this.engLang = engLang;
    this.rusLang = rusLang;
    this.transcription = transcription;
  }
}

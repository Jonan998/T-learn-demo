package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "words")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String engLang;
    private String rusLang;
    private String transcription;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<CardsWords> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<DictionaryWords> dictionaryWords = new ArrayList<>();

    public Word(String engLang,
                String rusLang,
                String transcription) {
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
    }

}


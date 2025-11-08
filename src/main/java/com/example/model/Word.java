package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String engLang;
    private String rusLang;
    private String transcription;

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CardsWords> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DictionaryWords> dictionaryWords = new ArrayList<>();

    public Word() {}

    public Word(String engLang,
                String rusLang,
                String transcription) {
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEngLang() { return engLang; }
    public void setEngLang(String engLang) { this.engLang = engLang; }

    public String getRusLang() { return rusLang; }
    public void setRusLang(String rusLang) { this.rusLang = rusLang; }

    public String getTranscription() { return transcription; }
    public void setTranscription(String transcription) { this.transcription = transcription; }

    public List<CardsWords> getCardsWords() { return cardsWords; }
    public void setCardsWords(List<CardsWords> cardsWords) { this.cardsWords = cardsWords; }

    public List<DictionaryWords> getDictionaryWords() { return dictionaryWords; }
    public void setDictionaryWords(List<DictionaryWords> dictionaryWords) { this.dictionaryWords = dictionaryWords; }
}


package com.example.model;

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
    private List<Cards_words> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "word", cascade = CascadeType.ALL)
    private List<Dictionary_words> dictionaryWords = new ArrayList<>();

    public Word() {}

    public Word(String engLang, String rusLang, String transcription) {
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
    }
    public Integer getID(){return id;}
    public void setId(Integer id) {this.id = id;}

    public String getEngLang(){return engLang;}
    public void setEngLang(String engLang){this.engLang = engLang;}

    public String getRusLang(){return rusLang;}
    public void setRusLang(String rusLang){this.rusLang = rusLang;}

    public String getTranscription(){return transcription;}
    public void setTranscription(String transcription){this.transcription = transcription;}

    public List<Cards_words> getCardsWords() { return cardsWords; }
    public void setCardsWords(List<Cards_words> cardsWords) { this.cardsWords = cardsWords; }

    public List<Dictionary_words> getDictionaryWords() { return dictionaryWords; }
    public void setDictionaryWords(List<Dictionary_words> dictionaryWords) {this.dictionaryWords = dictionaryWords;}
}

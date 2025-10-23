package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String engLang;
    private String rusLang;
    private String transcription;

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
}

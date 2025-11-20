package com.example.model;

import jakarta.persistence.*;
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

    public Word(String engLang, String rusLang, String transcription) {
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
    }
}
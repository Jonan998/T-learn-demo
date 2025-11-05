package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "dictionary_words")
public class Dictionary_words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;

    public Dictionary_words(){}

    public Dictionary_words(Word word,Dictionary dictionary){
        this.dictionary = dictionary;
        this.word = word;
    }

    public Integer getId(){return id;}
    public void setId(Integer id) {this.id = id;}

    public Word getWord() {return word;}
    public void setWord(Word word) {this.word = word;}

    public Dictionary getDictionary() {return dictionary;}
    public void setDictionary(Dictionary dictionary) {this.dictionary = dictionary;}
}

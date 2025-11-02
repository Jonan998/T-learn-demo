package com.example.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dictionary")
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String language;

    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL)
    private List<Cards_words> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL)
    private List<Dictionary_words> dictionaryWords = new ArrayList<>();

    public Dictionary(){}

    public Dictionary(String name, String description, String language){
        this.name = name;
        this.description = description;
        this.language = language;
    }

    public Integer getId() {return id;}
    public void setId(Integer id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public String getLanguage(){return language;}
    public void setLanguage(String language){this.language = language;}

    public List<Cards_words> getCardsWords() { return cardsWords; }
    public void setCardsWords(List<Cards_words> cardsWords) { this.cardsWords = cardsWords; }

    public List<Dictionary_words> getDictionaryWords() { return dictionaryWords; }
    public void setDictionaryWords(List<Dictionary_words> dictionaryWords) {this.dictionaryWords = dictionaryWords;}
}
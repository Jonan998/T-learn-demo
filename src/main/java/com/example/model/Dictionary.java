package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<CardsWords> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DictionaryWords> dictionaryWords = new ArrayList<>();

    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsersDictionaries> usersDictionaries = new ArrayList<>();

    public Dictionary(){}

    public Dictionary(String name,
                      String description,
                      String language){
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

    public List<CardsWords> getCardsWords() { return cardsWords; }
    public void setCardsWords(List<CardsWords> cardsWords) { this.cardsWords = cardsWords; }

    public List<DictionaryWords> getDictionaryWords() { return dictionaryWords; }
    public void setDictionaryWords(List<DictionaryWords> dictionaryWords) {this.dictionaryWords = dictionaryWords;}

    public List<UsersDictionaries> getUsersDictionaries() {return usersDictionaries;}
    public void setUsersDictionaries(List<UsersDictionaries> usersDictionaries) {this.usersDictionaries = usersDictionaries;}
}
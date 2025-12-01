package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dictionary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dictionary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String language;

    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<CardsWords> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<DictionaryWords> dictionaryWords = new ArrayList<>();

    @OneToMany(mappedBy = "dictionary", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<UsersDictionaries> usersDictionaries = new ArrayList<>();

    public Dictionary(String name, String description, String language) {
        this.name = name;
        this.description = description;
        this.language = language;
    }

    public Dictionary(String name){
        this.name = name;
    }
}
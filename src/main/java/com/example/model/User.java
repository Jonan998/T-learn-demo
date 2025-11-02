package com.example.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Cards_words> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Users_dictionaries> usersDictionaries = new ArrayList<>();

    public User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Cards_words> getCardsWords() { return cardsWords; }
    public void setCardsWords(List<Cards_words> cardsWords) { this.cardsWords = cardsWords; }

    public List<Users_dictionaries> getUsersDictionaries() {return usersDictionaries;}
    public void setUsersDictionaries(List<Users_dictionaries> usersDictionaries) {this.usersDictionaries = usersDictionaries;}
}
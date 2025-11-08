package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private List<CardsWords> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsersDictionaries> usersDictionaries = new ArrayList<>();

    public User() {}

    public User(String name,
                String password) {
        this.name = name;
        this.password = password;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<CardsWords> getCardsWords() { return cardsWords; }
    public void setCardsWords(List<CardsWords> cardsWords) { this.cardsWords = cardsWords; }

    public List<UsersDictionaries> getUsersDictionaries() {return usersDictionaries;}
    public void setUsersDictionaries(List<UsersDictionaries> usersDictionaries) {this.usersDictionaries = usersDictionaries;}
}
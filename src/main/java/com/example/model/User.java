package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
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

    @Column(name = "created_ad_new")
    private LocalDate created_at_new;

    @Column(name = "created_ad_repeat")
    private LocalDate created_at_repeat;

    @Column(name = "limit_new")
    private int limit_new;

    @Column(name = "limit_repeat")
    private int limit_repeat;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CardsWords> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UsersDictionaries> usersDictionaries = new ArrayList<>();

    public User() {}

    public User(String name,
                String password,
                LocalDate created_at_new,
                LocalDate created_at_repeat,
                int limit_new,
                int limit_repeat) {
        this.name = name;
        this.password = password;
        this.created_at_new = created_at_new;
        this.created_at_repeat = created_at_repeat;
        this.limit_new = limit_new;
        this.limit_repeat = limit_repeat;
    }
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDate getCreated_at_new() {return created_at_new;}
    public void setCreated_at_new(LocalDate created_at_new) {this.created_at_new = created_at_new;}

    public LocalDate getCreated_at_repeat() {return created_at_repeat;}
    public void setCreated_at_repeat(LocalDate created_at_repeat) {this.created_at_repeat = created_at_repeat;}

    public int getLimit_new() {return limit_new;}
    public void setLimit_new(int limit_new) {this.limit_new = limit_new;}

    public int getLimit_repeat() {return limit_repeat;}
    public void setLimit_repeat(int limit_repeat) {this.limit_repeat = limit_repeat;}

    public List<CardsWords> getCardsWords() { return cardsWords; }
    public void setCardsWords(List<CardsWords> cardsWords) { this.cardsWords = cardsWords; }

    public List<UsersDictionaries> getUsersDictionaries() {return usersDictionaries;}
    public void setUsersDictionaries(List<UsersDictionaries> usersDictionaries) {this.usersDictionaries = usersDictionaries;}
}
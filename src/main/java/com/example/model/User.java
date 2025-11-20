package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "created_ad_new")
    private LocalDate createdAtNew;

    @Column(name = "created_ad_repeat")
    private LocalDate createdAtRepeat;

    @Column(name = "limit_new")
    private int limitNew;

    @Column(name = "limit_repeat")
    private int limitRepeat;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<CardsWords> cardsWords = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<UsersDictionaries> usersDictionaries = new ArrayList<>();

    public User(String name,
                String password,
                LocalDate createdAtNew,
                LocalDate createdAtRepeat,
                int limitNew,
                int limitRepeat) {
        this.name = name;
        this.password = password;
        this.createdAtNew = createdAtNew;
        this.createdAtRepeat = createdAtRepeat;
        this.limitNew = limitNew;
        this.limitRepeat = limitRepeat;
    }
}
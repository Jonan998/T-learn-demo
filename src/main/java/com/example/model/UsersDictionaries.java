package com.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_dictionaries")
public class UsersDictionaries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;

    private String is_active;
    private int progress;

    public UsersDictionaries() {}

    public UsersDictionaries(User user,
                             Dictionary dictionary,
                             String is_active,
                             int progress){
        this.user = user;
        this.dictionary = dictionary;
        this.is_active = is_active;
        this.progress = progress;
    }

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}

    public User getUser() {return user;}
    public void setUser(User user) {this.user = user;}

    public Dictionary getDictionary() {return dictionary;}
    public void setDictionary(Dictionary dictionary) {this.dictionary = dictionary;}

    public String getIs_active() {return is_active;}
    public void setIs_active(String is_active) {this.is_active = is_active;}

    public int getProgress() {return progress;}
    public void setProgress(int progress) {this.progress = progress;}
}

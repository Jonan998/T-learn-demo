package com.example.model;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "cards_words")
public class Cards_words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int user_id;
    private int word_id;
    private int dictionary_id;
    private int study_lvl;
    private LocalDate next_review;

    public Cards_words() {}

    public Cards_words(int user_id, int word_id,int dictionary_id,int study_lvl, LocalDate next_review){
        this.user_id = user_id;
        this.word_id = word_id;
        this.dictionary_id = dictionary_id;
        this.study_lvl = study_lvl;
        this.next_review = next_review;
    }

    public Integer getId(){return id;}
    public void setId(Integer id){this.id = id;}

    public Integer getUserId(){return user_id;}
    public void setUser_id(int user_id){this.user_id = user_id;}

    public int getWord_id(){return word_id;}
    public void setWord_id(int word_id){this.word_id = word_id;}

    public int getDictionary_id(){return  dictionary_id;}
    public void setDictionary_id(int dictionary_id) {this.dictionary_id = dictionary_id;}

    public int getStudy_lvl() {return study_lvl;}
    public void setStudy_lvl(int study_lvl) {this.study_lvl = study_lvl;}

    public LocalDate getNext_review() {return next_review;}
    public void setNext_review(LocalDate next_review) {this.next_review = next_review;}
}
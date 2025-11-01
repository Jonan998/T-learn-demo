package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cards_words")
public class Cards_words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dictionary_id")
    private Dictionary dictionary;

    @Column(name = "study_lvl")
    private Integer studyLevel;

    @Column(name = "next_review")
    private LocalDate nextReview;

    public Cards_words() {}

    public Cards_words(User user, Word word, Dictionary dictionary, Integer studyLevel, LocalDate nextReview) {
        this.user = user;
        this.word = word;
        this.dictionary = dictionary;
        this.studyLevel = studyLevel;
        this.nextReview = nextReview;
    }

    public Cards_words(Integer user_id, Integer word_id, Integer dictionary_id, Integer studyLevel, LocalDate nextReview) {
        this.user = new User();
        this.user.setId(user_id);
        this.word = new Word();
        this.word.setId(word_id);
        this.dictionary = new Dictionary();
        this.dictionary.setId(dictionary_id);
        this.studyLevel = studyLevel;
        this.nextReview = nextReview;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Word getWord() { return word; }
    public void setWord(Word word) { this.word = word; }

    public Dictionary getDictionary() { return dictionary; }
    public void setDictionary(Dictionary dictionary) { this.dictionary = dictionary; }

    public Integer getStudyLevel() { return studyLevel; }
    public void setStudyLevel(Integer studyLevel) { this.studyLevel = studyLevel; }

    public LocalDate getNextReview() { return nextReview; }
    public void setNextReview(LocalDate nextReview) { this.nextReview = nextReview; }
}
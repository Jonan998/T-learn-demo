package com.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "cards_words")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardsWords {
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

    public CardsWords(User user,
                      Word word,
                      Dictionary dictionary,
                      Integer studyLevel,
                      LocalDate nextReview) {
        this.user = user;
        this.word = word;
        this.dictionary = dictionary;
        this.studyLevel = studyLevel;
        this.nextReview = nextReview;
    }
}
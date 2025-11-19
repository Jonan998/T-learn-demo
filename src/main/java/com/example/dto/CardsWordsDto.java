package com.example.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardsWordsDto {
    private Integer id;
    private Integer userId;
    private Integer wordId;
    private Integer dictionaryId;
    private Integer studyLevel;
    private LocalDate nextReview;

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getWordId() {
        return wordId;
    }

    public Integer getDictionaryId() {
        return dictionaryId;
    }

    public Integer getStudyLevel() {
        return studyLevel;
    }

    public LocalDate getNextReview() {
        return nextReview;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public void setDictionaryId(Integer dictionaryId) {
        this.dictionaryId = dictionaryId;
    }

    public void setStudyLevel(Integer studyLevel) {
        this.studyLevel = studyLevel;
    }

    public void setNextReview(LocalDate nextReview) {
        this.nextReview = nextReview;
    }
}
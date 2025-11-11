package com.example.dto;

import java.time.LocalDate;

public class CardsWordsDto {
    private Integer id;
    private Integer userId;          // вместо полного объекта User
    private Integer wordId;          // вместо полного объекта Word
    private Integer dictionaryId;    // вместо полного объекта Dictionary
    private Integer studyLevel;
    private LocalDate nextReview;
    
    public CardsWordsDto() {}
    
    public CardsWordsDto(Integer id, Integer userId, Integer wordId, Integer dictionaryId, 
                        Integer studyLevel, LocalDate nextReview) {
        this.id = id;
        this.userId = userId;
        this.wordId = wordId;
        this.dictionaryId = dictionaryId;
        this.studyLevel = studyLevel;
        this.nextReview = nextReview;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    
    public Integer getWordId() { return wordId; }
    public void setWordId(Integer wordId) { this.wordId = wordId; }
    
    public Integer getDictionaryId() { return dictionaryId; }
    public void setDictionaryId(Integer dictionaryId) { this.dictionaryId = dictionaryId; }
    
    public Integer getStudyLevel() { return studyLevel; }
    public void setStudyLevel(Integer studyLevel) { this.studyLevel = studyLevel; }
    
    public LocalDate getNextReview() { return nextReview; }
    public void setNextReview(LocalDate nextReview) { this.nextReview = nextReview; }
}
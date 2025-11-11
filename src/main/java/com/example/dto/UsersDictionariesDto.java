package com.example.dto;

public class UsersDictionariesDto {
    private Integer id;
    private Integer userId;          // вместо полного объекта User
    private Integer dictionaryId;    // вместо полного объекта Dictionary
    private String isActive;
    private Integer progress;
    
    public UsersDictionariesDto() {}
    
    public UsersDictionariesDto(Integer id, Integer userId, Integer dictionaryId, 
                               String isActive, Integer progress) {
        this.id = id;
        this.userId = userId;
        this.dictionaryId = dictionaryId;
        this.isActive = isActive;
        this.progress = progress;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    
    public Integer getDictionaryId() { return dictionaryId; }
    public void setDictionaryId(Integer dictionaryId) { this.dictionaryId = dictionaryId; }
    
    public String getIsActive() { return isActive; }
    public void setIsActive(String isActive) { this.isActive = isActive; }
    
    public Integer getProgress() { return progress; }
    public void setProgress(Integer progress) { this.progress = progress; }
}
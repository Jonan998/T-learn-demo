package com.example.dto;

public class DictionaryWordsDto {
    private Integer id;
    private Integer wordId;          // вместо полного объекта Word
    private Integer dictionaryId;    // вместо полного объекта Dictionary
    
    public DictionaryWordsDto() {}
    
    public DictionaryWordsDto(Integer id, Integer wordId, Integer dictionaryId) {
        this.id = id;
        this.wordId = wordId;
        this.dictionaryId = dictionaryId;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public Integer getWordId() { return wordId; }
    public void setWordId(Integer wordId) { this.wordId = wordId; }
    
    public Integer getDictionaryId() { return dictionaryId; }
    public void setDictionaryId(Integer dictionaryId) { this.dictionaryId = dictionaryId; }
}
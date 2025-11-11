package com.example.dto;

public class WordDto {
    private Integer id;
    private String engLang;
    private String rusLang;
    private String transcription;
    
    public WordDto() {}
    
    public WordDto(Integer id, String engLang, String rusLang, String transcription) {
        this.id = id;
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getEngLang() { return engLang; }
    public void setEngLang(String engLang) { this.engLang = engLang; }
    
    public String getRusLang() { return rusLang; }
    public void setRusLang(String rusLang) { this.rusLang = rusLang; }
    
    public String getTranscription() { return transcription; }
    public void setTranscription(String transcription) { this.transcription = transcription; }
}
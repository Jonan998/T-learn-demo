package com.example.dto;

public class WordDto {
    private Integer id;
    private int study_lvl;
    private String engLang;
    private String rusLang;
    private String transcription;
//    private int dictionaryId;

    public WordDto() {}

    public WordDto(Integer id, String engLang, String rusLang, String transcription) {
        this.id = id;
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
    }

    public WordDto(Integer id ,int study_lvl, String engLang, String rusLang, String transcription) {
        this.id = id;
        this.study_lvl = study_lvl;
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;

    }

//    public WordDto(Integer id, String engLang, String rusLang, String transcription, int dictionaryId) {
//        this.id = id;
//        this.engLang = engLang;
//        this.rusLang = rusLang;
//        this.transcription = transcription;
//        this.dictionaryId = dictionaryId;
//    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public int getStudy_lvl() {return study_lvl;}
    public void setStudy_lvl(int study_lvl) {this.study_lvl = study_lvl;}

    public String getEngLang() { return engLang; }
    public void setEngLang(String engLang) { this.engLang = engLang; }

    public String getRusLang() { return rusLang; }
    public void setRusLang(String rusLang) { this.rusLang = rusLang; }

    public String getTranscription() { return transcription; }
    public void setTranscription(String transcription) { this.transcription = transcription; }


    //    public int getDictionaryId() { return dictionaryId; }
//    public void setDictionaryId(int dictionaryId) { this.dictionaryId = dictionaryId; }

}
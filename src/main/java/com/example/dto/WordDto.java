package com.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordDto {
    private Integer id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer studyLvl;
    private String engLang;
    private String rusLang;
    private String transcription;

    @JsonIgnore
    private Double priority;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String example;

    public WordDto(Integer id, String engLang, String rusLang, String transcription) {
        this.id = id;
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
    }

    public WordDto(Integer id, int studyLvl, String engLang, String rusLang, String transcription) {
        this.id = id;
        this.studyLvl = studyLvl;
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
    }

    public WordDto(Integer id, int studyLvl, String engLang, String rusLang, String transcription, Double priority) {
        this.id = id;
        this.studyLvl = studyLvl;
        this.engLang = engLang;
        this.rusLang = rusLang;
        this.transcription = transcription;
        this.priority = priority;
    }

}
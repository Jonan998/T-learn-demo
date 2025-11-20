package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WordDto {
    private Integer id;
    private int study_lvl;
    private String engLang;
    private String rusLang;
    private String transcription;

    public Integer getId() {
        return id;
    }

    public String getEngLang() {
        return engLang;
    }

    public String getRusLang() {
        return rusLang;
    }

    public String getTranscription() {
        return transcription;
    }

}
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
}
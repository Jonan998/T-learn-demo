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
    private Integer userId;          // вместо полного объекта User
    private Integer wordId;          // вместо полного объекта Word
    private Integer dictionaryId;    // вместо полного объекта Dictionary
    private Integer studyLevel;
    private LocalDate nextReview;
}
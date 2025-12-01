package com.example.dto;

import lombok.*;
import java.time.LocalDateTime;

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
    private LocalDateTime nextReview;

    public CardsWordsDto(Integer wordId, Integer studyLevel) {
        this.wordId = wordId;
        this.studyLevel = studyLevel;
    }
}
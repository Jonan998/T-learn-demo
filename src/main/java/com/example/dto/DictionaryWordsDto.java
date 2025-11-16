package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DictionaryWordsDto {
    private Integer id;
    private Integer wordId;          // вместо полного объекта Word
    private Integer dictionaryId;    // вместо полного объекта Dictionary
}
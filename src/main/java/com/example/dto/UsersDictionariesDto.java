package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDictionariesDto {
    private Integer id;
    private Integer userId;          // вместо полного объекта User
    private Integer dictionaryId;    // вместо полного объекта Dictionary
    private String isActive;
    private Integer progress;
}
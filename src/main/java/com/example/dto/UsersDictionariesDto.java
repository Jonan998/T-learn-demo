package com.example.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersDictionariesDto {
    private Integer id;
    private Integer userId;
    private Integer dictionaryId;
    private Boolean isActive;
    private Integer progress;

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getDictionaryId() {
        return dictionaryId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public Integer getProgress() {
        return progress;
    }
}
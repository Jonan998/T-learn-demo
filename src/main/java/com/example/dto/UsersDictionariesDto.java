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
}

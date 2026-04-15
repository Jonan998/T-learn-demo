package ru.teducation.dto;

import lombok.Data;

@Data
public class AnswerRequestDto {
  private Integer wordId;
  private String userAnswer;
}

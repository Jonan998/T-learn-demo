package ru.teducation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponseDto {
  private boolean correct;
  private String correctAnswer;
}

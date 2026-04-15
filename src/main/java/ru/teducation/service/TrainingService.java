package ru.teducation.service;

import ru.teducation.dto.AnswerRequestDto;
import ru.teducation.dto.AnswerResponseDto;

public interface TrainingService {
  AnswerResponseDto checkAnswer(AnswerRequestDto request);
}

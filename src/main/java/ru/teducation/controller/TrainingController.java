package ru.teducation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.teducation.dto.AnswerRequestDto;
import ru.teducation.dto.AnswerResponseDto;
import ru.teducation.service.TrainingService;

@RestController
@RequestMapping("/learning/training")
@RequiredArgsConstructor
public class TrainingController {
  private final TrainingService trainingService;

  @PostMapping("/check")
  public AnswerResponseDto checkAnswer(@RequestBody AnswerRequestDto request) {
    return trainingService.checkAnswer(request);
  }
}

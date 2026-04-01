package ru.teducation.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teducation.dto.AnswerRequestDto;
import ru.teducation.dto.AnswerResponseDto;
import ru.teducation.exception.NotFoundException;
import ru.teducation.model.Word;
import ru.teducation.repository.WordRepository;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final WordRepository wordRepository;

    @Override
    public AnswerResponseDto checkAnswer(AnswerRequestDto request) {
        Word word =
                wordRepository
                        .findById(request.getWordId())
                        .orElseThrow(() -> new NotFoundException("Слово не найдено"));

        String actualWord = word.getEngLang();
        String userAnswer = Optional.ofNullable(request.getUserAnswer()).orElse("");
        boolean isCorrect = actualWord.trim().equalsIgnoreCase(userAnswer.trim());

        return new AnswerResponseDto(isCorrect, actualWord);
    }
}
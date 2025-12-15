package ru.teducation.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.teducation.dto.CardsWordsDto;
import ru.teducation.exception.NotFoundException;
import ru.teducation.mapper.CardsWordsMapper;
import ru.teducation.model.*;
import ru.teducation.model.CardsWords;
import ru.teducation.model.Dictionary;
import ru.teducation.model.User;
import ru.teducation.model.Word;
import ru.teducation.repository.*;
import ru.teducation.repository.CardsWordsRepository;
import ru.teducation.repository.DictionaryRepository;
import ru.teducation.repository.UserRepository;
import ru.teducation.repository.WordRepository;

@Slf4j
@Service
public class CardsWordsServiceImpl implements CardsWordsService {
  private final CardsWordsRepository cardsWordsRepository;
  private final UserRepository userRepository;
  private final WordRepository wordRepository;
  private final DictionaryRepository dictionaryRepository;
  private final CardsWordsMapper cardsWordsMapper;

  public CardsWordsServiceImpl(
      CardsWordsRepository cardsWordsRepository,
      UserRepository userRepository,
      WordRepository wordRepository,
      DictionaryRepository dictionaryRepository,
      CardsWordsMapper cardsWordsMapper) {
    this.cardsWordsRepository = cardsWordsRepository;
    this.userRepository = userRepository;
    this.wordRepository = wordRepository;
    this.dictionaryRepository = dictionaryRepository;
    this.cardsWordsMapper = cardsWordsMapper;
  }

  @Override
  public CardsWordsDto getCardsWords(int cardsWordsId) {
    CardsWords cardsWords = cardsWordsRepository.getByIdCardWords(cardsWordsId).orElse(null);
    return cardsWords != null ? cardsWordsMapper.toDto(cardsWords) : null;
  }

  @Override
  @Transactional
  public void createCardsWords(
      int userId, int wordId, int dictionaryId, int studyLvl, LocalDateTime nextReview) {

    Optional<User> user = userRepository.findById(userId);
    Optional<Word> word = wordRepository.findById(wordId);
    Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionaryId);

    CardsWords card =
        new CardsWords(user.get(), word.get(), dictionary.get(), studyLvl, nextReview);
    cardsWordsRepository.save(card);
  }

  @Transactional
  public CardsWordsDto createCardsWords(CardsWordsDto cardsWordsDto) {
    Optional<User> user = userRepository.findById(cardsWordsDto.getUserId());
    Optional<Word> word = wordRepository.findById(cardsWordsDto.getWordId());
    Optional<Dictionary> dictionary =
        dictionaryRepository.findById(cardsWordsDto.getDictionaryId());

    if (user.isPresent() && word.isPresent() && dictionary.isPresent()) {
      CardsWords card =
          new CardsWords(
              user.get(),
              word.get(),
              dictionary.get(),
              cardsWordsDto.getStudyLevel(),
              cardsWordsDto.getNextReview());
      CardsWords savedCard = cardsWordsRepository.save(card);
      return cardsWordsMapper.toDto(savedCard);
    }
    return null;
  }

  @Override
  @Transactional
  public void updateWordStatus(int userId, List<CardsWordsDto> updates) {
    log.info(
        "Обновление статуса слов: userId={}, количество обновлений={}", userId, updates.size());

    for (CardsWordsDto dto : updates) {
      log.debug("Обработка слова wordId={} для userId={}", dto.getWordId(), userId);

      CardsWords card =
          cardsWordsRepository
              .findByUserIdAndWordId(userId, dto.getWordId())
              .orElseThrow(
                  () -> {
                    log.error("Карточка не найдена: userId={}, wordId={}", userId, dto.getWordId());
                    return new NotFoundException("Карточка не найдена");
                  });

      int oldLvl = card.getStudyLevel();
      int newLvl = dto.getStudyLevel();

      log.debug(
          "Старый уровень={}, новый уровень={} для wordId={}", oldLvl, newLvl, dto.getWordId());

      card.setStudyLevel(newLvl);

      LocalDateTime next;
      switch (newLvl) {
        case 1 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMinutes(5);
        case 2 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(1);
        case 3 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1);
        case 4 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusWeeks(1);
        case 5 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMonths(1);
        case 6 -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusMonths(3);
        default -> next = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
      }

      card.setNextReview(next);

      log.debug("Следующее повторение для wordId={} назначено на {}", dto.getWordId(), next);

      cardsWordsRepository.save(card);
    }
    log.info("Обновление статусов слов завершено: userId={}, обновлено={}", userId, updates.size());
  }
}

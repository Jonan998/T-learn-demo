package ru.teducation.mapper;

import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.teducation.dto.CardsWordsDto;
import ru.teducation.model.CardsWords;
import ru.teducation.model.Dictionary;
import ru.teducation.model.User;
import ru.teducation.model.Word;

@Mapper(componentModel = "spring")
public interface CardsWordsMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "wordId", source = "word.id")
  @Mapping(target = "dictionaryId", source = "dictionary.id")
  CardsWordsDto toDto(CardsWords entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "studyLevel", constant = "0")
  @Mapping(target = "nextReview", source = "now")
  @Mapping(target = "user", source = "user")
  @Mapping(target = "word", source = "word")
  @Mapping(target = "dictionary", source = "dictionary")
  CardsWords toEntity(User user, Word word, Dictionary dictionary, LocalDateTime now);
}

package ru.teducation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.teducation.dto.CardsWordsDto;
import ru.teducation.model.CardsWords;

@Mapper(componentModel = "spring")
public interface CardsWordsMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "wordId", source = "word.id")
  @Mapping(target = "dictionaryId", source = "dictionary.id")
  CardsWordsDto toDto(CardsWords entity);

  CardsWords toEntity(CardsWordsDto dto);
}

package com.example.mapper;

import com.example.dto.CardsWordsDto;
import com.example.model.CardsWords;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardsWordsMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "wordId", source = "word.id")
  @Mapping(target = "dictionaryId", source = "dictionary.id")
  CardsWordsDto toDto(CardsWords entity);

  CardsWords toEntity(CardsWordsDto dto);
}

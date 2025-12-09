package com.example.mapper;

import com.example.dto.DictionaryWordsDto;
import com.example.model.DictionaryWords;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DictionaryWordsMapper {

  @Mapping(
      target = "wordId",
      expression = "java(entity.getWord() != null ? entity.getWord().getId() : null)")
  @Mapping(
      target = "dictionaryId",
      expression = "java(entity.getDictionary() != null ? entity.getDictionary().getId() : null)")
  DictionaryWordsDto toDto(DictionaryWords entity);

  @Mapping(target = "word", ignore = true)
  @Mapping(target = "dictionary", ignore = true)
  DictionaryWords toEntity(DictionaryWordsDto dto);
}

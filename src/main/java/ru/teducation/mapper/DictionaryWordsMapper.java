package ru.teducation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.teducation.dto.DictionaryWordsDto;
import ru.teducation.model.DictionaryWords;

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

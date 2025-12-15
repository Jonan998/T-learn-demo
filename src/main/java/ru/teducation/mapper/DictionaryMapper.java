package ru.teducation.mapper;

import org.mapstruct.Mapper;
import ru.teducation.dto.DictionaryDto;
import ru.teducation.model.Dictionary;

@Mapper(componentModel = "spring")
public interface DictionaryMapper {

  DictionaryDto toDto(Dictionary entity);

  Dictionary toEntity(DictionaryDto dto);
}

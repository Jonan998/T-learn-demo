package ru.teducation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.teducation.dto.DictionaryDto;
import ru.teducation.model.Dictionary;
import ru.teducation.model.User;

@Mapper(componentModel = "spring")
public interface DictionaryMapper {

  DictionaryDto toDto(Dictionary entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "language", constant = "en-ru")
  @Mapping(target = "isPublic", constant = "false")
  @Mapping(target = "ownerId", source = "owner")
  @Mapping(target = "name", source = "dto.name")
  @Mapping(target = "description", source = "dto.description")
  Dictionary toEntity(DictionaryDto dto, User owner);
}

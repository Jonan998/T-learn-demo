package com.example.mapper;

import com.example.dto.UsersDictionariesDto;
import com.example.model.UsersDictionaries;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsersDictionariesMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "dictionaryId", source = "dictionary.id")
  @Mapping(target = "isActive", source = "isActive")
  UsersDictionariesDto toDto(UsersDictionaries entity);

  @Mapping(target = "user", ignore = true)
  @Mapping(target = "dictionary", ignore = true)
  @Mapping(target = "isActive", source = "isActive")
  UsersDictionaries toEntity(UsersDictionariesDto dto);
}

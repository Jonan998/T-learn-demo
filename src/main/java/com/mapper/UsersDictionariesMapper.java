package com.example.mapper;

import com.example.model.UsersDictionaries;
import com.example.dto.UsersDictionariesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsersDictionariesMapper {
    
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "dictionaryId", source = "dictionary.id")
    @Mapping(target = "isActive", source = "is_active") 
    UsersDictionariesDto toDto(UsersDictionaries entity);
    
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "dictionary", ignore = true)
    @Mapping(target = "is_active", source = "isActive") 
    UsersDictionaries toEntity(UsersDictionariesDto dto);
}
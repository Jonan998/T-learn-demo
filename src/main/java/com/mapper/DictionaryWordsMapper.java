package com.example.mapper;

import com.example.model.DictionaryWords;
import com.example.dto.DictionaryWordsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DictionaryWordsMapper {
    
    @Mapping(target = "wordId", source = "word.id")
    @Mapping(target = "dictionaryId", source = "dictionary.id")
    DictionaryWordsDto toDto(DictionaryWords entity);
    
    @Mapping(target = "word", ignore = true)
    @Mapping(target = "dictionary", ignore = true)
    DictionaryWords toEntity(DictionaryWordsDto dto);
}
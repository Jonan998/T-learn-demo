// господи помоги мне 
package com.example.mapper;

import com.example.model.CardsWords;
import com.example.model.User;
import com.example.dto.CardsWordsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardsWordsMapper {
    
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "wordId", source = "word.id")
    @Mapping(target = "dictionaryId", source = "dictionary.id")
    CardsWordsDto toDto(CardsWords entity);
    
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "word", ignore = true)
    @Mapping(target = "dictionary", ignore = true)
    CardsWords toEntity(CardsWordsDto dto);
}
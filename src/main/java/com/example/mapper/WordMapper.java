package com.example.mapper;

import com.example.model.Word;
import com.example.dto.WordDto;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WordMapper {
    
    WordDto toDto(Word entity);
    
    Word toEntity(WordDto dto);
    
    List<WordDto> toDtoList(List<Word> entities);
}
package com.example.mapper;

import com.example.dto.WordDto;
import com.example.model.Word;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WordMapper {

  WordDto toDto(Word entity);

  Word toEntity(WordDto dto);

  List<WordDto> toDtoList(List<Word> entities);
}

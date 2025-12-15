package ru.teducation.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.teducation.dto.WordDto;
import ru.teducation.model.Word;

@Mapper(componentModel = "spring")
public interface WordMapper {

  WordDto toDto(Word entity);

  Word toEntity(WordDto dto);

  List<WordDto> toDtoList(List<Word> entities);
}

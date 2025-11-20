package com.example.mapper;

import com.example.model.CardsWords;
import com.example.dto.CardsWordsDto;
import org.mapstruct.Mapper;
import com.example.model.Dictionary;

@Mapper(componentModel = "spring")
public interface CardsWordsMapper {
    
    default CardsWordsDto toDto(CardsWords entity) {
        if (entity == null) {
            return null;
        }
        
        CardsWordsDto dto = new CardsWordsDto();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUser() != null ? entity.getUser().getId() : null);
        dto.setWordId(entity.getWord() != null ? entity.getWord().getId() : null);
        dto.setDictionaryId(entity.getDictionary() != null ? entity.getDictionary().getId() : null);
        dto.setStudyLevel(entity.getStudyLevel());
        dto.setNextReview(entity.getNextReview());
        return dto;
    }
    
    default CardsWords toEntity(CardsWordsDto dto) {
        if (dto == null) {
            return null;
        }
        
        CardsWords cardsWords = new CardsWords();
        cardsWords.setId(dto.getId());
        cardsWords.setStudyLevel(dto.getStudyLevel());
        cardsWords.setNextReview(dto.getNextReview());
        return cardsWords;
    }
}
package com.example.mapper;

import com.example.model.Dictionary;
import com.example.dto.DictionaryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DictionaryMapper {
    
    DictionaryDto toDto(Dictionary entity);
    
    Dictionary toEntity(DictionaryDto dto);
}
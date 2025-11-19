package com.example.mapper;

import com.example.model.User;
import com.example.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserDto toDto(User entity);
    
    User toEntity(UserDto dto);
    
    default void updateEntityFromDto(UserDto dto, User entity) {
        if (dto == null) return;
        
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
    }
}
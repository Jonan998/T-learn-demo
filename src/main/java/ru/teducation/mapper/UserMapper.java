package ru.teducation.mapper;

import org.mapstruct.Mapper;
import ru.teducation.dto.UserDto;
import ru.teducation.model.User;

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

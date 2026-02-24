package ru.teducation.service;

import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.teducation.dto.UserDto;
import ru.teducation.dto.UserLimitsView;
import ru.teducation.mapper.UserMapper;
import ru.teducation.model.User;
import ru.teducation.repository.UserRepository;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final UserMapper userMapper;

  public UserServiceImpl(
      UserRepository repository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.userMapper = userMapper;
  }

  @Override
  public UserDto getUser(int userId) {
    User user = repository.findById(userId).orElse(null);
    return user != null ? userMapper.toDto(user) : null;
  }

  @Override
  public void createUser(
      String name,
      String password,
      LocalDateTime createdAtNew,
      LocalDateTime createdAtRepeat,
      int limitNew,
      int limitRepeat) {
    String encodedPassword = passwordEncoder.encode(password);
    repository.save(
        new User(name, encodedPassword, createdAtNew, createdAtRepeat, limitNew, limitRepeat));
  }

  public UserDto createUser(UserDto userDto, String rawPassword) {
    String encodedPassword = passwordEncoder.encode(rawPassword);
    User user =
        new User(
            userDto.getName(),
            encodedPassword,
            userDto.getCreatedAtNew(),
            userDto.getCreatedAtRepeat(),
            userDto.getLimitNew(),
            userDto.getLimitRepeat());
    User savedUser = repository.save(user);
    return userMapper.toDto(savedUser);
  }

  @Override
  public UserDto getUserLimits(int userId) {
    log.info("Запрос лимитов пользователя userId={}", userId);

    UserLimitsView limitsView = repository.findUserLimits(userId);

    if (limitsView == null) {
      log.warn("Лимиты для userId={} не найдены", userId);
      return null;
    }

    log.debug(
        "Лимиты: new={}, repeat={} для userId={}",
        limitsView.getLimitNew(),
        limitsView.getLimitRepeat(),
        userId);

    UserDto dto = new UserDto();

    dto.setLimitNew(limitsView.getLimitNew());
    dto.setLimitRepeat(limitsView.getLimitRepeat());

    return dto;
  }

  @Override
  public void updateUserSettings(int userId, UserDto dto) {
    log.info("Обновление настроек пользователя userId={}", userId);

    User user = repository.findById(userId).orElse(null);

    if (user == null) {
      log.error("Не удалось обновить настройки — пользователь {} не найден", userId);
      return;
    }

    log.debug(
        "Полученные параметры для обновления: name={}, limitNew={}, limitRepeat={}",
        dto.getName(),
        dto.getLimitNew(),
        dto.getLimitRepeat());

    if (StringUtils.isEmpty(dto.getPassword())) {
      log.warn("Попытка изменить настройки без текущего пароля userId={}", userId);
      throw new IllegalArgumentException("Текущий пароль обязателен для изменения настроек");
    }

    if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
      log.warn("Неверный текущий пароль userId={}", userId);
      throw new IllegalArgumentException("Неверный текущий пароль");
    }

    if (dto.getName() != null) {
      log.debug("Обновление имени: {} -> {}", user.getName(), dto.getName());
      user.setName(dto.getName());
    }

    if (dto.getLimitNew() != null) {
      log.debug("Обновление limitNew: {} -> {}", user.getLimitNew(), dto.getLimitNew());
      user.setLimitNew(dto.getLimitNew());
    }

    if (dto.getLimitRepeat() != null) {
      log.debug("Обновление limitRepeat: {} -> {}", user.getLimitRepeat(), dto.getLimitRepeat());
      user.setLimitRepeat(dto.getLimitRepeat());
    }

    if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {

      if (passwordEncoder.matches(dto.getNewPassword(), user.getPassword())) {
        log.warn("Новый пароль не должен совпадать с прошлым");
        throw new IllegalArgumentException("Новый пароль не должен совпадать с прошлым");
      }

      user.setPassword(passwordEncoder.encode(dto.getNewPassword()));

      log.info("Пароль пользователя userId={} успешно обновлён", userId);
    }

    repository.save(user);

    log.info("Настройки пользователя userId={} успешно обновлены", userId);
  }
}

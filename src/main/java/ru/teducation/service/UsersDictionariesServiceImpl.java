package ru.teducation.service;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.teducation.dto.UsersDictionariesDto;
import ru.teducation.mapper.UsersDictionariesMapper;
import ru.teducation.model.Dictionary;
import ru.teducation.model.User;
import ru.teducation.model.UsersDictionaries;
import ru.teducation.repository.DictionaryRepository;
import ru.teducation.repository.UserRepository;
import ru.teducation.repository.UsersDictionariesRepository;

@Service
public class UsersDictionariesServiceImpl implements UsersDictionariesService {
  private final UsersDictionariesRepository repository;
  private final DictionaryRepository dictionaryRepository;
  private final UserRepository userRepository;
  private final UsersDictionariesMapper usersDictionariesMapper;

  public UsersDictionariesServiceImpl(
      UsersDictionariesRepository repository,
      DictionaryRepository dictionaryRepository,
      UserRepository userRepository,
      UsersDictionariesMapper usersDictionariesMapper) {
    this.repository = repository;
    this.dictionaryRepository = dictionaryRepository;
    this.userRepository = userRepository;
    this.usersDictionariesMapper = usersDictionariesMapper;
  }

  @Override
  public UsersDictionariesDto getUsersDictionaries(int usersDictionariesId) {
    UsersDictionaries usersDictionaries =
        repository.getByIdUsersDict(usersDictionariesId).orElse(null);
    return usersDictionaries != null ? usersDictionariesMapper.toDto(usersDictionaries) : null;
  }

  @Override
  @Transactional
  public void createUsersDictionaries(int userId, int dictionaryId, String isActive, int progress) {
    Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionaryId);
    Optional<User> user = userRepository.findById(userId);

    Boolean isActiveBoolean = Boolean.valueOf(isActive);
    repository.save(new UsersDictionaries(user.get(), dictionary.get(), isActiveBoolean, progress));
  }

  @Transactional
  public UsersDictionariesDto createUsersDictionaries(UsersDictionariesDto usersDictionariesDto) {
    Optional<Dictionary> dictionary =
        dictionaryRepository.findById(usersDictionariesDto.getDictionaryId());
    Optional<User> user = userRepository.findById(usersDictionariesDto.getUserId());

    if (dictionary.isPresent() && user.isPresent()) {
      UsersDictionaries usersDictionaries =
          new UsersDictionaries(
              user.get(),
              dictionary.get(),
              usersDictionariesDto.getIsActive(),
              usersDictionariesDto.getProgress());
      UsersDictionaries savedUsersDictionaries = repository.save(usersDictionaries);
      return usersDictionariesMapper.toDto(savedUsersDictionaries);
    }
    return null;
  }
}

package com.example.service;

import com.example.model.Dictionary;
import com.example.model.User;
import com.example.model.UsersDictionaries;
import com.example.dto.UsersDictionariesDto;
import com.example.mapper.UsersDictionariesMapper;
import com.example.repository.DictionaryRepository;
import com.example.repository.UserRepository;
import com.example.repository.UsersDictionariesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsersDictionariesServiceImpl implements UsersDictionariesService {
    private final UsersDictionariesRepository repository;
    private final DictionaryRepository dictionaryRepository;
    private final UserRepository userRepository;
    private final UsersDictionariesMapper usersDictionariesMapper;

    public UsersDictionariesServiceImpl(UsersDictionariesRepository repository, 
                                       DictionaryRepository dictionaryRepository, 
                                       UserRepository userRepository,
                                       UsersDictionariesMapper usersDictionariesMapper) {
        this.repository = repository;
        this.dictionaryRepository = dictionaryRepository;
        this.userRepository = userRepository;
        this.usersDictionariesMapper = usersDictionariesMapper;
    }

    @Override
    public UsersDictionariesDto getUsersDictionaries(int users_dictionariesId) {
        UsersDictionaries usersDictionaries = repository.getByIdUsersDict(users_dictionariesId).orElse(null);
        return usersDictionaries != null ? usersDictionariesMapper.toDto(usersDictionaries) : null;
    }

    @Override
    @Transactional
    public void createUsersDictionaries(int user_id, int dictionary_id, String is_active, int progress) {
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionary_id);
        Optional<User> user = userRepository.findById(user_id);

        repository.save(new UsersDictionaries(user.get(), dictionary.get(), is_active, progress));
    }
    
    @Transactional
    public UsersDictionariesDto createUsersDictionaries(UsersDictionariesDto usersDictionariesDto) {
        Optional<Dictionary> dictionary = dictionaryRepository.findById(usersDictionariesDto.getDictionaryId());
        Optional<User> user = userRepository.findById(usersDictionariesDto.getUserId());

        if (dictionary.isPresent() && user.isPresent()) {
            UsersDictionaries usersDictionaries = new UsersDictionaries(
                user.get(),
                dictionary.get(),
                usersDictionariesDto.getIsActive(),
                usersDictionariesDto.getProgress()
            );
            UsersDictionaries savedUsersDictionaries = repository.save(usersDictionaries);
            return usersDictionariesMapper.toDto(savedUsersDictionaries);
        }
        return null;
    }
}
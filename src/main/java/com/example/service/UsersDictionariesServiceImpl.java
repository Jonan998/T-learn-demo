package com.example.service;

import com.example.model.Dictionary;
import com.example.model.User;
import com.example.model.UsersDictionaries;
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

    public UsersDictionariesServiceImpl(UsersDictionariesRepository repository, DictionaryRepository dictionaryRepository, UserRepository userRepository){
        this.repository = repository;
        this.dictionaryRepository = dictionaryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UsersDictionaries getUsersDictionaries(int users_dictionariesId){
        return repository.getByIdUsersDict(users_dictionariesId).orElse(null);
    }

    @Override
    @Transactional
    public void createUsersDictionaries(int user_id, int dictionary_id, String is_active, int progress){
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionary_id);
        Optional<User> user = userRepository.findById(user_id);

        repository.save(new UsersDictionaries(user.get(),dictionary.get(),is_active,progress));
    }
}

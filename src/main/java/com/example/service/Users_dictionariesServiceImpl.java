package com.example.service;

import com.example.model.Dictionary;
import com.example.model.User;
import com.example.model.Users_dictionaries;
import com.example.repository.DictionaryRepository;
import com.example.repository.UserRepository;
import com.example.repository.Users_dictionariesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class Users_dictionariesServiceImpl implements Users_dictionariesService{
    private final Users_dictionariesRepository repository;
    private final DictionaryRepository dictionaryRepository;
    private final UserRepository userRepository;
    public Users_dictionariesServiceImpl(Users_dictionariesRepository repository,DictionaryRepository dictionaryRepository, UserRepository userRepository){
        this.repository = repository;
        this.dictionaryRepository = dictionaryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Users_dictionaries getUsers_dictionaries(int users_dictionariesId){
        return repository.findById(users_dictionariesId).orElse(null);
    }

    @Override
    @Transactional
    public void createUsers_dictionaries(int user_id, int dictionary_id, String is_active, int progress){
        Optional<Dictionary> dictionary = dictionaryRepository.findById(dictionary_id);
        Optional<User> user = userRepository.findById(user_id);
        repository.save(new Users_dictionaries(user.get(),dictionary.get(),is_active,progress));
    }
}

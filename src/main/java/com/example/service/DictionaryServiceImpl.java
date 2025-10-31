package com.example.service;


import com.example.model.Dictionary;
import com.example.repository.DictionaryRepository;
import org.springframework.stereotype.Service;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    private final DictionaryRepository repository;

    public DictionaryServiceImpl(DictionaryRepository repository){
        this.repository = repository;
    }
    @Override
    public Dictionary getDictionary(int dictionaryId){
        return repository.findById(dictionaryId).orElse(null);
    }
    @Override
    public void createDictionary(String name, String description, String language){
        repository.save(new Dictionary(name,description,language));
    }
}

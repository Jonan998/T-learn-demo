package com.example.service;

import com.example.model.Word;
import com.example.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository repository;

    public WordServiceImpl(WordRepository repository) {
        this.repository = repository;
    }

    @Override
    public Word getWord(int wordid) {
        return repository.findById(wordid).orElse(null);
    }

    @Override
    public void createWord(String eng,
                           String rus,
                           String transcription) {
        repository.save(new Word(eng, rus, transcription));
    }

    @Override
    public List<Word> getRandWords(int limit){
        return repository.GetWords(limit);
    }
}

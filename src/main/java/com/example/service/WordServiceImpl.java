package com.example.service;

import com.example.model.Word;
import com.example.dto.WordDto;
import com.example.mapper.WordMapper;
import com.example.repository.WordRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WordServiceImpl implements WordService {
    private final WordRepository repository;
    private final WordMapper wordMapper;

    public WordServiceImpl(WordRepository repository, WordMapper wordMapper) {
        this.repository = repository;
        this.wordMapper = wordMapper;
    }

    @Override
    public WordDto getWord(int wordId) {
        Word word = repository.findById(wordId).orElse(null);
        return word != null ? wordMapper.toDto(word) : null;
    }

    @Override
    public void createWord(String eng, String rus, String transcription) {
        repository.save(new Word(eng, rus, transcription));
    }

    @Override
    public List<WordDto> getRandWords(int limit) {
        List<Word> words = repository.getWords(limit);
        return wordMapper.toDtoList(words);
    }
    
    public WordDto createWord(WordDto wordDto) {
        Word word = new Word(wordDto.getEngLang(), wordDto.getRusLang(), wordDto.getTranscription());
        Word savedWord = repository.save(word);
        return wordMapper.toDto(savedWord);
    }
}
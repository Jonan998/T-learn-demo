package com.example.service;


import com.example.model.Cards_words;
import com.example.repository.Cards_wordsRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class Cards_wordsServiceImpl implements Cards_wordsService{
    private final Cards_wordsRepository repository;


    public Cards_wordsServiceImpl(Cards_wordsRepository repository) {
        this.repository = repository;

    }

    @Override
    public Cards_words getCards_words(int cards_wordsid){
        return repository.findById(cards_wordsid).orElse(null);
    }

    @Override
    public void createCards_words(int user_id, int word_id, int dictionary_id,
                                  int study_lvl, LocalDate next_review){


        Cards_words card = new Cards_words(user_id, word_id, dictionary_id, study_lvl, next_review);
        repository.save(card);
    }
}

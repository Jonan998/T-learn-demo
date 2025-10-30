package com.example.service;

import com.example.model.Word;

import java.util.List;

public interface WordService {
        Word getWord(int wordid);
        void createWord(String eng, String rus, String transcription);
        List<Word> GetRandWords(int limit);
}

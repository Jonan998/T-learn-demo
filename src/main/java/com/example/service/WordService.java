package com.example.service;

import com.example.dto.WordDto;
import com.example.model.Word;

import java.util.List;

public interface WordService {
        WordDto getWord(int wordid);
        void createWord(String eng, String rus, String transcription);
        List<WordDto> getRandWords(int limit);
}

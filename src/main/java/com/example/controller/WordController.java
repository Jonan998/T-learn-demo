package com.example.controller;

import com.example.model.Word;
import com.example.service.WordServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {
    private final WordServiceImpl service;

    public WordController(WordServiceImpl service) {
        this.service = service;
    }

    @PostMapping
    public void createWord(@RequestParam String eng,
                           @RequestParam String rus,
                           @RequestParam String transcription) {
        service.createWord(eng, rus, transcription);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public Word getWord(@PathVariable int id) {
        return service.getWord(id);
    }

    @GetMapping(value = "/random/{count}", produces = "application/json; charset=UTF-8")
    public List<Word> getRandWords(
            @PathVariable("count") int count) {
        return service.getRandWords(count);
    }
}

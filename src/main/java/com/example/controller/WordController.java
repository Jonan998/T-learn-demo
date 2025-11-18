package com.example.controller;

import com.example.dto.WordDto;
import com.example.model.Word;
import com.example.service.WordService;
import com.example.service.WordServiceImpl;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/words")
public class WordController {
    private final WordService service;

    public WordController(WordService service) {
        this.service = service;
    }

    @PostMapping
    public void createWord(@RequestParam String eng,
                           @RequestParam String rus,
                           @RequestParam String transcription) {
        service.createWord(eng, rus, transcription);
    }

    @GetMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public WordDto getWord(@PathVariable int id) {
        return service.getWord(id);
    }

    @GetMapping(value = "/random/{count}", produces = "application/json; charset=UTF-8")
    public List<WordDto> getRandWords(
            @PathVariable("count") int count) {
        return service.getRandWords(count);
    }
}

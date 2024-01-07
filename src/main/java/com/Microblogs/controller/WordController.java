package com.Microblogs.controller;

import com.Microblogs.service.Word.WordDto;
import com.Microblogs.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/words")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping
    public ResponseEntity<List<WordDto>> getMostUsedWords(@RequestParam(name = "size") int size) {
        return new ResponseEntity<>(wordService.getMostUsedWords(size), HttpStatus.OK);
    }
}

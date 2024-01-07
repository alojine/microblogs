package com.Microblogs.service;

import com.Microblogs.service.Word.WordDto;

import java.util.List;

public interface WordService {
    List<WordDto> getMostUsedWords(int size);
}

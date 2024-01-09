package com.Microblogs.controller;

import com.Microblogs.service.Word.WordDto;
import com.Microblogs.service.WordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class WordControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WordService wordService;

    @Test
    void whenGetMostUsedWords_thenReturnWordList() throws Exception {
        when(wordService.getMostUsedWords(3)).thenReturn(provideWordDto());

        mvc.perform(MockMvcRequestBuilders.get("/api/v1/words")
                        .param("size", "3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].word").value("apple"))
                .andExpect(jsonPath("$[0].numberOfUsages").value(10))
                .andExpect(jsonPath("$[1].word").value("banana"))
                .andExpect(jsonPath("$[1].numberOfUsages").value(8))
                .andExpect(jsonPath("$[2].word").value("orange"))
                .andExpect(jsonPath("$[2].numberOfUsages").value(6));
    }

    static List<WordDto> provideWordDto() {
        return List.of(
                new WordDto("apple", 10),
                new WordDto("banana", 8),
                new WordDto("orange", 6)
        );
    }
}
package com.Microblogs.config;

import com.Microblogs.exception.InternalApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Set;

@Configuration
public class StopWordsConfig {

    private final ObjectMapper objectMapper;

    @Autowired
    public StopWordsConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public Set<String> stopWords() {
        try {
            File file = ResourceUtils.getFile("classpath:static/stop_words_english.json");
            return objectMapper.readValue(file, new TypeReference<>(){});
        } catch (IOException ex) {
            throw new InternalApiException("Failed to load stop words from file");
        }
    }
}

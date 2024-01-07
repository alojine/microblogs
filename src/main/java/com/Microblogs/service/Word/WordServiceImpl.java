package com.Microblogs.service.Word;

import com.Microblogs.model.Blog;
import com.Microblogs.service.BlogService;
import com.Microblogs.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;

@Service
public class WordServiceImpl implements WordService {
    private final BlogService blogService;

    private final Set<String> stopWords;

    @Autowired
    public WordServiceImpl(BlogService blogService, Set<String> stopWords) {
        this.blogService = blogService;
        this.stopWords = stopWords;
    }

    @Override
    public List<WordDto> getMostUsedWords(int size) {
        Map<String, Integer> wordFrequency = new HashMap<>();

        for (Blog blog : blogService.getAll()) {
            String textOfBlog = showerText(blog.getTitle() + " " + blog.getBody());
            String[] blogWords = textOfBlog.split("\\s+");

            for (String word : blogWords) {
                if (!stopWords.contains(word)) {
                    wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                }
            }
        }

        return wordFrequency.entrySet().stream()
                .map(entry -> new WordDto(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(WordDto::numberOfUsages).reversed())
                .limit(size)
                .toList();
    }

    private String showerText(String text) {
        text = text.toLowerCase();

        // Replace accent(ę,ė,š), remove non-alphanumeric characters.
        text = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .replaceAll("[^a-zA-Z0-9\\s]", "")
                .replaceAll("\\s+", " ");

        return text;
    }
}
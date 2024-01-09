package com.Microblogs.service;

import com.Microblogs.config.StopWordsConfig;
import com.Microblogs.model.Blog;
import com.Microblogs.model.User;
import com.Microblogs.service.Word.WordDto;
import com.Microblogs.service.Word.WordServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = StopWordsConfig.class, loader = AnnotationConfigContextLoader.class)
class WordServiceImplTest {

    @InjectMocks
    private WordServiceImpl wordService;

    @Mock
    private BlogService blogService;

    @MockBean
    private ObjectMapper objectMapper;

    @Mock
    Set<String> stopWords;

    @BeforeEach
    void setup() {

    }

    static Method getShowerTextMethod() throws NoSuchMethodException {
        Method method = WordServiceImpl.class.getDeclaredMethod("showerText", String.class);
        method.setAccessible(true);
        return method;
    }

    static Stream<String> provideDirtyText() {
        return Stream.of(
                "This.,;'][ is[]{}?<>/*- a. Test",
                "THIS IS A TEST",
                "this      is .   .  .  A   TEST",
                "this@@##%%^^&&**(())__++==-- is a test",
                "thįš įš ą tėšt!"
        );
    }

    @ParameterizedTest
    @MethodSource("provideDirtyText")
    void test_showerText(String argument) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String expectedResult = "this is a test";
        WordServiceImpl wordService = new WordServiceImpl(blogService, stopWords);

        assertThat((String) getShowerTextMethod().invoke(wordService, argument)).isEqualTo(expectedResult);
    }

    @Test
    void getMostUsedWords_shouldReturnCorrectListSize() {
        when(blogService.getAll()).thenReturn(List.of(provideDesktopBlog(), provideLaptopBlog(), provideStopWordsBlog()));

        assertThat(wordService.getMostUsedWords(1)).isEqualTo(List.of(provideDesktopWordDto()));
        assertThat(wordService.getMostUsedWords(2)).isEqualTo(List.of(provideDesktopWordDto(), provideLaptopWordDto()));
    }

    static WordDto provideDesktopWordDto() {
        return new WordDto(
                "desktop",
                5
        );
    }

    static WordDto provideLaptopWordDto() {
        return new WordDto(
                "laptop",
                4
        );
    }

    static Blog provideDesktopBlog() {
        Blog blog = new Blog();
        blog.setId(UUID.fromString("977a9c13-414b-4cb2-8ad4-380ef6c2f4c3"));
        blog.setUser(provideUser());
        blog.setTitle("Desktop");
        blog.setBody("Desktop Desktop Desktop Desktop");
        blog.setCreatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));
        blog.setUpdatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));

        return blog;
    }

    static Blog provideLaptopBlog() {
        Blog blog = new Blog();
        blog.setId(UUID.fromString("977a9c13-414b-4cb2-8ad4-380ef6c2f4c3"));
        blog.setUser(provideUser());
        blog.setTitle("Laptop");
        blog.setBody("Laptop Laptop Laptop");
        blog.setCreatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));
        blog.setUpdatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));

        return blog;
    }

    static Blog provideStopWordsBlog() {
        Blog blog = new Blog();
        blog.setId(UUID.fromString("977a9c13-414b-4cb2-8ad4-380ef6c2f4c3"));
        blog.setUser(provideUser());
        blog.setTitle("is my you");
        blog.setBody("stop please I my to make");
        blog.setCreatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));
        blog.setUpdatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));

        return blog;
    }

    static User provideUser() {
        User user = new User();
        user.setId(UUID.fromString("21c22fa2-2050-4815-9dbb-3de0389a6bd7"));
        user.setUsername("tom");
        user.setEmail("tom@gmail.com");
        user.setPassword("tomtom");
        user.setCreatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));
        user.setUpdatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808") );

        return user;
    }
}
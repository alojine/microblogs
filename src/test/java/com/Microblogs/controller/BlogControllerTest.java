package com.Microblogs.controller;

import com.Microblogs.controller.Blog.BlogDto;
import com.Microblogs.exception.ResourceNotFoundException;
import com.Microblogs.mapper.BlogMapper;
import com.Microblogs.model.Blog;
import com.Microblogs.model.User;
import com.Microblogs.service.BlogService;
import com.Microblogs.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.sql.Timestamp;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BlogService blogService;

    @MockBean
    private UserService userService;

    @MockBean
    private BlogMapper blogMapper;

    @Test
    void whenCreateBlog_withCorrectBody_thenReturnIsCreated() throws Exception {
        when(userService.getByEmail(any())).thenReturn(provideUser());
        when(blogMapper.blogDTOtoBlog(any())).thenReturn(provideBlog());
        when(blogService.save(any())).thenReturn(provideBlog());

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/blogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provideBlogDto())))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(provideBlog().getTitle()))
                .andExpect(jsonPath("$.body").value(provideBlog().getBody()))
                .andReturn();
    }


    @Test
    void whenUpdateBlog_withCorrectBody_thenReturnIsOk() throws Exception {
        when(blogService.update(any())).thenReturn(provideBlog());
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/blogs/977a9c13-414b-4cb2-8ad4-380ef6c2f4c3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(provideBlogDto())))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void whenDeleteBlog_withCorrectId_thenReturnIsOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/api/v1/blogs/977a9c13-414b-4cb2-8ad4-380ef6c2f4c3")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    private static BlogDto provideBlogDto() {
        return new BlogDto(
                "tomtom@gmail.com",
                "Title",
                "Body"
        );
    }

    private static Blog provideBlog() {
        Blog blog = new Blog();
        blog.setId(UUID.fromString("977a9c13-414b-4cb2-8ad4-380ef6c2f4c3"));
        blog.setUser(provideUser());
        blog.setTitle("Title");
        blog.setBody("Body");
        blog.setCreatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));
        blog.setUpdatedAt(Timestamp.valueOf("2024-01-06 20:49:25.164808"));

        return blog;
    }

    private static User provideUser() {
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
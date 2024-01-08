package com.Microblogs.service;

import com.Microblogs.controller.Blog.BlogDto;
import com.Microblogs.exception.RequestValidationException;
import com.Microblogs.exception.ResourceNotFoundException;
import com.Microblogs.model.Blog;
import com.Microblogs.model.User;
import com.Microblogs.repository.BlogRepository;
import com.Microblogs.service.Impl.BlogServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class BlogServiceImplTest {

    @InjectMocks
    private BlogServiceImpl blogService;

    @Mock
    private BlogRepository blogRepository;

    @Test
    void whenGetAll_thenReturnAllBlogs() {
        when(blogRepository.findAll()).thenReturn(List.of(provideBlog()));

        assertThat(blogService.getAll()).isEqualTo(List.of(provideBlog()));
    }

    @Test
    void whenGetById_withNotExistentId_thenThrowsResourceValidationException() {
        UUID notExistentUserId = UUID.fromString("0629a3ca-5452-4264-8dfb-67ead125d134");
        when(blogRepository.getBlogById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> blogService.getById(notExistentUserId));
    }

    @Test
    void whenGetById_withExistingId_thenReturnCorrectUser() {
        when(blogRepository.getBlogById(any())).thenReturn(Optional.of(provideBlog()));

        assertThat(blogService.getById(provideBlog().getId())).isEqualTo(provideBlog());
    }

    @Test
    void whenSave_withEmptyBodyOrTitle_thenReturnRequestValidationException() {
        Blog blogWithEmptyBody = provideBlog();
        blogWithEmptyBody.setBody("");
        Blog blogWithEmptyTitle = provideBlog();
        blogWithEmptyTitle.setTitle("");

        assertThrows(RequestValidationException.class, () -> blogService.save(blogWithEmptyBody));
        assertThrows(RequestValidationException.class, () -> blogService.save(blogWithEmptyTitle));
    }

    @Test
    void whenSave_withValidBlog_thenReturnCorrectBlog() {
        when(blogRepository.save(any())).thenReturn(provideBlog());

        assertThat(blogService.save(provideBlog())).isEqualTo(provideBlog());
    }

    @Test
    void whenUpdate_withEmptyBodyOrTitle_thenReturnRequestValidationException() {
        when(blogRepository.getBlogById(any())).thenReturn(Optional.of(provideBlog()));

        Blog blogWithEmptyBody = provideBlog();
        blogWithEmptyBody.setBody("");
        Blog blogWithEmptyTitle = provideBlog();
        blogWithEmptyTitle.setTitle("");

        assertThrows(RequestValidationException.class, () -> blogService.update(blogWithEmptyBody));
        assertThrows(RequestValidationException.class, () -> blogService.update(blogWithEmptyTitle));
    }

    @Test
    void whenUpdate_withUserNotBeingBlogOwner_thenReturnsValidationError() {
        when(blogRepository.getBlogById(any())).thenReturn(Optional.of(provideBlog()));

        User notBlogOwner = provideUser();
        notBlogOwner.setUsername("tomNotOwner");
        Blog blogWithBadUser = provideBlog();
        blogWithBadUser.setUser(notBlogOwner);

        assertThrows(RequestValidationException.class, () -> blogService.update(blogWithBadUser));
    }

    @Test
    void whenUpdate_withValidEditedBlog_thenReturnsCorrectBlog() {
        Blog changedBlog = provideBlog();
        changedBlog.setTitle("MyNewTitle");

        when(blogRepository.getBlogById(any())).thenReturn(Optional.of(provideBlog()));
        when(blogRepository.save(any())).thenReturn(changedBlog);

        assertThat(blogService.update(changedBlog)).isEqualTo(changedBlog);
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
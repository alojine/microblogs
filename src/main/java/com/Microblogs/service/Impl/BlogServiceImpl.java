package com.Microblogs.service.Impl;

import com.Microblogs.exception.RequestValidationException;
import com.Microblogs.exception.ResourceNotFoundException;
import com.Microblogs.model.Blog;
import com.Microblogs.repository.BlogRepository;
import com.Microblogs.service.BlogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public List<Blog> getAll() {
        return blogRepository.findAll();
    }

    @Override
    public Blog getById(UUID id) {
        return blogRepository.getBlogById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Blog with Id: %s does not exist", id)));
    }

    @Override
    public Blog save(Blog blog) {
        if (blog.getTitle().isEmpty()) {
            throw new RequestValidationException("Blog title cannot be set as empty");
        } else if (blog.getBody().isEmpty()) {
            throw new RequestValidationException("Blog body cannot be set as empty");
        }

        return blogRepository.save(blog);
    }

    @Override
    public Blog update(Blog blog) {
        Blog dbBlog = getById(blog.getId());

        if (!dbBlog.getUser().equals(blog.getUser())) {
            throw new RequestValidationException("Only blog owner can update the blog");
        }

        if (blog.getTitle().isEmpty()) {
            throw new RequestValidationException("Blog title cannot be set as empty");
        } else if (blog.getBody().isEmpty()) {
            throw new RequestValidationException("Blog body cannot be set as empty");
        }

        dbBlog.setTitle(blog.getTitle());
        dbBlog.setBody(blog.getBody());
        dbBlog.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        logger.info("Blog has been updated");

        return blogRepository.save(dbBlog);
    }

    @Override
    public void delete(UUID id) {
        blogRepository.delete(getById(id));
    }
}

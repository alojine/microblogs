package com.Microblogs.service.Impl;

import com.Microblogs.repository.BlogRepository;
import com.Microblogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public void deleteBlog(UUID id) {
        blogRepository.deleteById(id);
    }
}

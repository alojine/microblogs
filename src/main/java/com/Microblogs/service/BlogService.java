package com.Microblogs.service;

import com.Microblogs.model.Blog;

import java.util.List;
import java.util.UUID;

public interface BlogService {

    List<Blog> getAll();

    Blog getById(UUID id);

    Blog save(Blog blog);

    Blog update(Blog blog);

    void delete(UUID id);
}

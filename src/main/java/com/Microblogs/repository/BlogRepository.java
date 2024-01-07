package com.Microblogs.repository;

import com.Microblogs.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlogRepository extends JpaRepository<Blog, UUID> {
    Optional<Blog> getBlogById(UUID id);
}

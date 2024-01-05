package com.Microblogs.controller;

import com.Microblogs.exception.RequestValidationException;
import com.Microblogs.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController("api/v1/blogs")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

// TODO
//    @PostMapping
//    public ResponseEntity<?> add(){
//
//    }
//
//    @PutMapping
//    public ResponseEntity<?> update() {
//
//    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<HttpStatus> deleteBlog(@PathVariable("blogId") String blogId ) {
        UUID id;
        try {
            id = UUID.fromString(blogId);
        } catch (IllegalArgumentException ex) {
            throw new RequestValidationException("Invalid UUID format provided");
        }
        blogService.deleteBlog(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.Microblogs.controller.Blog;

import com.Microblogs.mapper.BlogMapper;
import com.Microblogs.model.Blog;
import com.Microblogs.service.BlogService;
import com.Microblogs.service.UserService;
import com.Microblogs.utility.UUIDHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/blogs")
public class BlogController {

    private final BlogService blogService;

    private final UserService userService;

    private final BlogMapper blogMapper;

    @Autowired
    public BlogController(BlogService blogService, UserService userService, BlogMapper blogMapper) {
        this.blogService = blogService;
        this.userService = userService;
        this.blogMapper = blogMapper;
    }

    @GetMapping
    public ResponseEntity<List<BlogDto>> getAllBlogs() {
        return new ResponseEntity<>(blogMapper.blogListToBlogDtoList(blogService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<BlogDto> getBlogById(@PathVariable("blogId") String blogId) {
        return new ResponseEntity<>(blogMapper.blogToBlogDto(blogService.getById(UUIDHelper.parseUUID(blogId))), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody BlogDto blogDto){
        Blog blog = blogMapper.blogDTOtoBlog(blogDto);
        blog.setUser(userService.getByEmail(blogDto.email()));

        return new ResponseEntity<>(blogService.save(blog), HttpStatus.CREATED);
    }

    @PutMapping("/{blogId}")
    public ResponseEntity<Blog> updateBlog(
            @PathVariable("blogId") String blogId,
            @RequestBody BlogDto blogDto
    ) {
        Blog blog = blogMapper.blogDTOtoBlog(blogDto);
        blog.setId(blogService.getById(UUIDHelper.parseUUID(blogId)).getId());
        blog.setUser(userService.getByEmail(blogDto.email()));

        return new ResponseEntity<>(blogService.update(blog), HttpStatus.OK);
    }

    @DeleteMapping("/{blogId}")
    public ResponseEntity<HttpStatus> deleteBlog(@PathVariable("blogId") String blogId ) {
        blogService.delete(UUIDHelper.parseUUID(blogId));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

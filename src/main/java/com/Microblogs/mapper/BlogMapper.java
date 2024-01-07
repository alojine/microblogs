package com.Microblogs.mapper;

import com.Microblogs.controller.Blog.BlogDto;
import com.Microblogs.model.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BlogMapper {

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "body", source = "dto.body")
    Blog blogDTOtoBlog(BlogDto dto);
}

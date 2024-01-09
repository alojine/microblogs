package com.Microblogs.mapper;

import com.Microblogs.controller.Blog.BlogDto;
import com.Microblogs.model.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface BlogMapper {

    @Mapping(target = "title", source = "dto.title")
    @Mapping(target = "body", source = "dto.body")
    Blog blogDTOtoBlog(BlogDto dto);

    @Mapping(target = "title", source = "entity.title")
    @Mapping(target = "body", source = "entity.body")
    @Mapping(target = "email", source = "entity.user.email")
    BlogDto blogToBlogDto(Blog entity);

    List<BlogDto> blogListToBlogDtoList(List<Blog> blogEntities);
}

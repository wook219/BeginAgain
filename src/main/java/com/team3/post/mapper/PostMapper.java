package com.team3.post.mapper;

import com.team3.post.entity.PostDto;
import com.team3.post.entity.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostMapper {

    // PostEntity -> PostDto
    @Mapping(source = "user.nickname", target = "nickname")
    PostDto toPostDto(PostEntity postEntity);

    // PostDto -> PostEntity
    PostEntity toPostEntity(PostDto postDto);
}

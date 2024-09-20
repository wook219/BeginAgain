package com.team3.post.mapper;

import com.team3.post.entity.PostPhotoDto;
import com.team3.post.entity.PostPhotoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostPhotoMapper {

    @Mapping(source = "post.postId", target = "postId")
    PostPhotoDto toPostPhotoDto(PostPhotoEntity postPhotoEntity);

    PostPhotoEntity toPostPhotoEntity(PostPhotoDto postPhotoDto);
}

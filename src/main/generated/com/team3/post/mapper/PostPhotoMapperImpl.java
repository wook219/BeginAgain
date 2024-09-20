package com.team3.post.mapper;

import com.team3.post.entity.PostEntity;
import com.team3.post.entity.PostPhotoDto;
import com.team3.post.entity.PostPhotoEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-20T14:27:34+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PostPhotoMapperImpl implements PostPhotoMapper {

    @Override
    public PostPhotoDto toPostPhotoDto(PostPhotoEntity postPhotoEntity) {
        if ( postPhotoEntity == null ) {
            return null;
        }

        PostPhotoDto postPhotoDto = new PostPhotoDto();

        postPhotoDto.setPostId( postPhotoEntityPostPostId( postPhotoEntity ) );
        postPhotoDto.setPhotoId( postPhotoEntity.getPhotoId() );
        postPhotoDto.setImagePath( postPhotoEntity.getImagePath() );

        return postPhotoDto;
    }

    @Override
    public PostPhotoEntity toPostPhotoEntity(PostPhotoDto postPhotoDto) {
        if ( postPhotoDto == null ) {
            return null;
        }

        PostPhotoEntity postPhotoEntity = new PostPhotoEntity();

        postPhotoEntity.setPhotoId( postPhotoDto.getPhotoId() );
        postPhotoEntity.setImagePath( postPhotoDto.getImagePath() );

        return postPhotoEntity;
    }

    private Integer postPhotoEntityPostPostId(PostPhotoEntity postPhotoEntity) {
        if ( postPhotoEntity == null ) {
            return null;
        }
        PostEntity post = postPhotoEntity.getPost();
        if ( post == null ) {
            return null;
        }
        Integer postId = post.getPostId();
        if ( postId == null ) {
            return null;
        }
        return postId;
    }
}

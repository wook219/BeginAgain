package com.team3.post.mapper;

import com.team3.board.BoardEntity;
import com.team3.post.entity.PostDto;
import com.team3.post.entity.PostEntity;
import com.team3.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-20T14:13:17+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDto toPostDto(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setNickname( postEntityUserNickname( postEntity ) );
        postDto.setBoardId( postEntityBoardBoardId( postEntity ) );
        postDto.setUserId( postEntityUserId( postEntity ) );
        postDto.setPostId( postEntity.getPostId() );
        postDto.setTitle( postEntity.getTitle() );
        postDto.setContent( postEntity.getContent() );
        postDto.setViews( postEntity.getViews() );
        postDto.setCreatedAt( postEntity.getCreatedAt() );
        postDto.setUpdatedAt( postEntity.getUpdatedAt() );

        return postDto;
    }

    @Override
    public PostEntity toPostEntity(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }

        PostEntity.PostEntityBuilder postEntity = PostEntity.builder();

        postEntity.postId( postDto.getPostId() );
        postEntity.title( postDto.getTitle() );
        postEntity.content( postDto.getContent() );
        postEntity.views( postDto.getViews() );

        return postEntity.build();
    }

    private String postEntityUserNickname(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }
        User user = postEntity.getUser();
        if ( user == null ) {
            return null;
        }
        String nickname = user.getNickname();
        if ( nickname == null ) {
            return null;
        }
        return nickname;
    }

    private Integer postEntityBoardBoardId(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }
        BoardEntity board = postEntity.getBoard();
        if ( board == null ) {
            return null;
        }
        Integer boardId = board.getBoardId();
        if ( boardId == null ) {
            return null;
        }
        return boardId;
    }

    private Integer postEntityUserId(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }
        User user = postEntity.getUser();
        if ( user == null ) {
            return null;
        }
        Integer id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

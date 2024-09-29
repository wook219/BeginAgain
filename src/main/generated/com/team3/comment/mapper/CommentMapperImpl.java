package com.team3.comment.mapper;

import com.team3.comment.entity.Comment;
import com.team3.comment.entity.CommentDto;
import com.team3.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-22T22:26:24+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDto toCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDto commentDto = new CommentDto();

        commentDto.setNickname( commentUserNickname( comment ) );
        commentDto.setCommentId( comment.getCommentId() );
        commentDto.setContent( comment.getContent() );
        commentDto.setUpdatedAt( comment.getUpdatedAt() );

        return commentDto;
    }

    @Override
    public Comment toComment(CommentDto commentDto) {
        if ( commentDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.commentId( commentDto.getCommentId() );
        comment.content( commentDto.getContent() );

        return comment.build();
    }

    private String commentUserNickname(Comment comment) {
        if ( comment == null ) {
            return null;
        }
        User user = comment.getUser();
        if ( user == null ) {
            return null;
        }
        String nickname = user.getNickname();
        if ( nickname == null ) {
            return null;
        }
        return nickname;
    }
}

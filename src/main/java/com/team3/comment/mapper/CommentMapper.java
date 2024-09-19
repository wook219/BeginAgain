package com.team3.comment.mapper;

import com.team3.comment.entity.Comment;
import com.team3.comment.entity.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.nickname", target = "nickname")
    CommentDto toCommentDto(Comment comment);

    Comment toComment(CommentDto commentDto);
}

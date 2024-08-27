package com.team3.comment.mapper;

import com.team3.comment.entity.Comment;
import com.team3.comment.entity.CommentDto;

public interface CommentMapper {
    CommentDto toDto(Comment comment);
    Comment toEntity(CommentDto commentDto);
}

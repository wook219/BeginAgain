package com.team3.comment.mapper;

import com.team3.comment.dto.CommentDto;
import com.team3.comment.entity.Comment;

public interface CommentMapper {
    CommentDto toDto(Comment comment);
    Comment toEntity(CommentDto commentDto);
}

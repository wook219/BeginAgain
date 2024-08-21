package com.team3.comment.mapper;

import com.yourpackage.comment.dto.CommentDto;
import com.yourpackage.comment.entity.Comment;

public interface CommentMapper {
    CommentDto toDto(Comment comment);
    Comment toEntity(CommentDto commentDto);
}

package com.team3.comment.mapper;

import com.team3.comment.entity.Comment;
import com.team3.comment.entity.CommentDto;

public class CommentMapperImpl implements CommentMapper {
    @Override
    public CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDto dto = new CommentDto();
        dto.setUserId(comment.getCommentId());
        dto.setContent(comment.getContent());
        // dto.setAuthorUsername(comment.getAuthor().getUsername());
        return dto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto) {
        return null;
    }


}

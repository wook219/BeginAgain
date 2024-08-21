package com.team3.comment.mapper;

import com.team3.comment.dto.CommentDto;
import com.team3.comment.entity.Comment;

public class CommentMapperImpl implements CommentMapper {
    @Override
    public CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        // dto.setAuthorUsername(comment.getAuthor().getUsername());
        return dto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto) {
        if (commentDto == null) {
            return null;
        }
        // User user = new User(null, commentDto.getAuthorUsername());
        // return new Comment(commentDto.getId(), commentDto.getContent(), user);
        return null;
    }
}

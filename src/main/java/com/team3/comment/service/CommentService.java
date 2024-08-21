package com.team3.comment.service;

import com.team3.comment.entity.Comment;
import com.team3.comment.entity.User;
import com.team3.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment addComment(String content, User author) {
        Comment newComment = new Comment(null, content, author);
        return commentRepository.save(newComment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment updateComment(Long id, String newContent) {
        Comment comment = commentRepository.findById(id);
        if (comment != null) {
            comment.setContent(newContent);
            return commentRepository.save(comment);
        }
        return null;
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}

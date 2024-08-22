package com.team3.comment.service;

import com.team3.comment.entity.Comment;
import com.team3.comment.repository.CommentRepository;
import com.team3.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Comment updateComment(Integer id, String newContent) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("댓글을 찾을 수 없습니다."));
        if (comment != null) {
            comment.setContent(newContent);
            return commentRepository.save(comment);
        }
        return null;
    }

    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}

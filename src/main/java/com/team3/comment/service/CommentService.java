package com.team3.comment.service;

import com.team3.comment.entity.Comment;
import com.team3.comment.entity.CommentDto;
import com.team3.comment.repository.CommentRepository;
import com.team3.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 생성자를 통한 의존성 주입
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // 댓글 추가 (User 객체 사용)
    public Comment addComment(CommentDto commentDto) { //content와 author(작성자)를 인자로 받아 새로운 Comment 객체를 생성하고, 이를 데이터베이스에 저장합니다.

        Comment newComment = new Comment(
                commentDto.getContent(),
                commentDto.getUserId(),
                commentDto.getPostId()
        );  // 새로운 댓글 객체 생성
        return commentRepository.save(newComment);  // 댓글을 저장하고 저장된 댓글 반환
    }



    // 모든 댓글 조회
    public List<Comment> getAllComments() { //데이터베이스에서 모든 댓글을 조회하여 리스트로 반환.
        return commentRepository.findAll();  // 모든 댓글을 데이터베이스에서 조회하여 반환
    }


    // 댓글 수정
    public Comment updateComment(Integer id, String newContent) { //특정 ID의 댓글을 수정.
        Comment comment = commentRepository.findById(id) //주어진 ID로 댓글을 검색. 댓글이 없으면 NoSuchElementException을 발생.
                .orElseThrow(() -> new NoSuchElementException("댓글을 찾을 수 없습니다."));  // ID로 댓글을 조회하고, 존재하지 않으면 예외 발생
        comment.setContent(newContent);  // 댓글 내용 수정
        return commentRepository.save(comment);  // 수정된 댓글을 저장하고 반환
    }

    // 댓글 삭제
    public void deleteComment(Integer id) { //특정 ID의 댓글을 삭제합니다.
        commentRepository.deleteById(id);  // ID로 댓글을 삭제
    }


    // ID로 특정 댓글 조회
    public Comment getCommentById(Integer id) { //특정 ID의 댓글을 조회합니다. 댓글이 없으면 예외를 발생시킵니다.
        return commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글을 찾을 수 없습니다."));  // ID로 댓글을 조회하고, 존재하지 않으면 예외 발생
    }


}



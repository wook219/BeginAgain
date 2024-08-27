package com.team3.comment.repository;

import com.team3.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // JpaRepository를 상속받아 기본 CRUD 메서드를 사용
    List<Comment> findByPostIdOrderByCreatedAtDesc(Integer postId);

}

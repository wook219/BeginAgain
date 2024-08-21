package com.team3.comment.repository;

import com.yourpackage.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // JpaRepository를 상속받아 기본 CRUD 메서드를 사용
}

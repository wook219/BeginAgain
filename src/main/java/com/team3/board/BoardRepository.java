package com.team3.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    @Query("SELECT b FROM BoardEntity b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    Page<BoardEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}

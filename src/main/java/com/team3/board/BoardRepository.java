package com.team3.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    // 삭제되지 않은 게시판 중 제목 또는 내용에 keyword가 포함된 게시판 검색
    @Query("SELECT b FROM BoardEntity b WHERE (b.isDeleted = false) AND (b.title LIKE %:keyword% OR b.content LIKE %:keyword%)")
    Page<BoardEntity> searchByKeywordAndIsDeletedFalse(@Param("keyword") String keyword, Pageable pageable);

    // 삭제되지 않은 게시판만 조회
    Page<BoardEntity> findByIsDeletedFalse(Pageable pageable);
}

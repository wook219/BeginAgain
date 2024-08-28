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

    // deletedAt이 null인 경우를 조회하고 createdAt 내림차순으로 정렬
    List<BoardEntity> findByDeletedAtIsNullOrderByCreatedAtDesc();

    // deletedAt이 null인 경우를 조회하고 createdAt 오름차순으로 정렬
    List<BoardEntity> findByDeletedAtIsNullOrderByCreatedAtAsc();

    // createdAt 내림차순으로 정렬하여 페이징 처리된 결과 반환
    Page<BoardEntity> findByDeletedAtIsNullOrderByCreatedAtDesc(Pageable pageable);

    // createdAt 오름차순으로 정렬하여 페이징 처리된 결과 반환
    Page<BoardEntity> findByDeletedAtIsNullOrderByCreatedAtAsc(Pageable pageable);

    // 제목으로 검색 기능 추가 (키워드를 포함하는)
    @Query("SELECT b FROM BoardEntity b WHERE b.title LIKE %:keyword% AND b.deletedAt IS NULL")
    List<BoardEntity> searchByName(@Param("keyword") String keyword);

    // 내용으로 검색 기능 추가 (키워드를 포함하는)
    @Query("SELECT b FROM BoardEntity b WHERE b.content LIKE %:keyword% AND b.deletedAt IS NULL")
    List<BoardEntity> searchByDescription(@Param("keyword") String keyword);

    // 게시판을 이름과 설명을 포함하여 검색하고 페이지네이션 적용
    @Query("SELECT b FROM BoardEntity b WHERE (b.title LIKE %:keyword% OR b.content LIKE %:keyword%) AND b.deletedAt IS NULL")
    Page<BoardEntity> searchByNameOrDescription(@Param("keyword") String keyword, Pageable pageable);

}

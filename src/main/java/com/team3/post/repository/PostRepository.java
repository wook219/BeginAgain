package com.team3.post.repository;

import com.team3.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    Page<PostEntity> findByBoardId(Integer boardId, Pageable pageable);
    List<PostEntity> findByBoardId(Integer boardId);
    List<PostEntity> findByBoardIdOrderByUpdatedAtDesc(Integer boardId);
    List<PostEntity> findByBoardIdOrderByViewsDesc(Integer boardId);

    @Query("SELECT p FROM PostEntity p WHERE p.content LIKE %:keyword% AND p.boardId = :boardId")
    List<PostEntity> searchByContent(@Param("boardId")Integer boardId, @Param("keyword")String keyword);
}

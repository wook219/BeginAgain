package com.team3.post.repository;

import com.team3.post.entity.PostEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
    List<PostEntity> findByBoardId(Integer boardId);
    List<PostEntity> findByBoardIdOrderByUpdatedAtDesc(Integer boardId);
    List<PostEntity> findByBoardIdOrderByViewsDesc(Integer boardId);
}

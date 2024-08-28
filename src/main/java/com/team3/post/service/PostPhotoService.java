package com.team3.post.service;

import com.team3.post.entity.PostPhotoEntity;
import com.team3.post.repository.PostPhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostPhotoService {
    private final PostPhotoRepository postPhotoRepository;

    public PostPhotoService(PostPhotoRepository postPhotoRepository) {
        this.postPhotoRepository = postPhotoRepository;
    }

    public List<PostPhotoEntity> getPhotosByPostId(Integer postId){
        return postPhotoRepository.findByPost_PostId(postId);
    }
}

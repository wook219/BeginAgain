package com.team3.post.service;

import com.team3.post.entity.PostPhotoDto;
import com.team3.post.entity.PostPhotoEntity;
import com.team3.post.mapper.PostPhotoMapper;
import com.team3.post.repository.PostPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostPhotoService {

    @Autowired
    PostPhotoMapper postPhotoMapper;

    private final PostPhotoRepository postPhotoRepository;

    public PostPhotoService(PostPhotoRepository postPhotoRepository) {
        this.postPhotoRepository = postPhotoRepository;
    }

    public List<PostPhotoDto> getPhotosByPostId(Integer postId){
        List<PostPhotoEntity> posts = postPhotoRepository.findByPost_PostId(postId);
        List<PostPhotoDto> postDtos = new ArrayList<>();

        for (PostPhotoEntity post : posts) {
            postDtos.add(postPhotoMapper.toPostPhotoDto(post));
        }
        return postDtos;
    }
}

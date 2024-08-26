package com.team3.post.service;

import com.team3.post.entity.PostDto;
import com.team3.post.entity.PostEntity;
import com.team3.post.repository.PostRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    //게시글 생성
    public void createPost(PostDto postDto){

        PostEntity newPost = new PostEntity(
                postDto.getTitle(),
                postDto.getContent(),
                postDto.getUserId(),
                postDto.getBoardId()
        );

        postRepository.save(newPost);

    }

    //BoardId에 따른 게시글리스트 조회
    public List<PostDto> getPostsByBoardId(Integer boardId){
        //boardId에 해당하는 게시글 목록을 DB에서 조회
        List<PostEntity> posts = postRepository.findByBoardId(boardId);

        //PostDto 객체를 담을 리스트 생성
        List<PostDto> postDtos = new ArrayList<>();

        // 조회한 게시글 목록 루프 돌면서 postDtos에 추가
        for (PostEntity post : posts) {
            PostDto postDto = new PostDto();
            postDto.setPostId(post.getPostId());
            postDto.setTitle(post.getTitle());
            postDto.setContent(post.getContent());
            postDto.setViews(post.getViews());
            postDto.setUserId(post.getUserId());
            postDto.setBoardId(post.getBoardId());
            postDto.setCreatedAt(post.getCreatedAt());
            postDto.setUpdatedAt(post.getUpdatedAt());

            postDtos.add(postDto);
        }

        //변환된 postDtos를 반환
        return postDtos;
    }

    public List<PostDto> getPostsByBoardIdUpdatedAtDesc(Integer boardId){
        //boardId에 해당하는 게시글 목록을 DB에서 조회
        List<PostEntity> posts = postRepository.findByBoardIdOrderByUpdatedAtDesc(boardId);

        //PostDto 객체를 담을 리스트 생성
        List<PostDto> postDtos = new ArrayList<>();

        // 조회한 게시글 목록 루프 돌면서 postDtos에 추가
        for (PostEntity post : posts) {
            PostDto postDto = new PostDto();
            postDto.setPostId(post.getPostId());
            postDto.setTitle(post.getTitle());
            postDto.setViews(post.getViews());
            postDto.setContent(post.getContent());
            postDto.setUserId(post.getUserId());
            postDto.setBoardId(post.getBoardId());
            postDto.setCreatedAt(post.getCreatedAt());
            postDto.setUpdatedAt(post.getUpdatedAt());

            postDtos.add(postDto);
        }

        //변환된 postDtos를 반환
        return postDtos;
    }

    public List<PostDto> getPostsByBoardIdViewsDesc(Integer boardId){
        //boardId에 해당하는 게시글 목록을 DB에서 조회
        List<PostEntity> posts = postRepository.findByBoardIdOrderByViewsDesc(boardId);

        //PostDto 객체를 담을 리스트 생성
        List<PostDto> postDtos = new ArrayList<>();

        // 조회한 게시글 목록 루프 돌면서 postDtos에 추가
        for (PostEntity post : posts) {
            PostDto postDto = new PostDto();
            postDto.setPostId(post.getPostId());
            postDto.setTitle(post.getTitle());
            postDto.setViews(post.getViews());
            postDto.setContent(post.getContent());
            postDto.setUserId(post.getUserId());
            postDto.setBoardId(post.getBoardId());
            postDto.setCreatedAt(post.getCreatedAt());
            postDto.setUpdatedAt(post.getUpdatedAt());

            postDtos.add(postDto);
        }

        //변환된 postDtos를 반환
        return postDtos;
    }

    //postId에 따른 게시글 단건 조회
    public PostEntity getPostByPostId(Integer postId){
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));;

        return post;
    }

    //게시글 수정
    public void modifyPost(PostDto postDto){

        PostEntity post = postRepository.findById(postDto.getPostId())
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postRepository.save(post);
    }

    //게시글 삭제
    public void deletePost(Integer postId){
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));

        postRepository.delete(post);
    }

    // 수정 or 삭제하려는 글의 작성자인지 아닌지 체크하는 메서드
    public boolean userCheck(Integer sessionId, Integer userId){

        if(Objects.equals(sessionId, userId)){
            return true;
        }
        return false;
    }

}
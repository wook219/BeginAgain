package com.team3.post.service;

import com.team3.board.BoardEntity;
import com.team3.board.BoardRepository;
import com.team3.post.entity.PostDto;
import com.team3.post.entity.PostEntity;
import com.team3.post.entity.PostPhotoEntity;
import com.team3.post.mapper.PostMapper;
import com.team3.post.repository.PostPhotoRepository;
import com.team3.post.repository.PostRepository;
import com.team3.user.entity.User;
import com.team3.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.*;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostPhotoRepository postPhotoRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Autowired
    private PostMapper postMapper;

    public PostService(PostRepository postRepository, PostPhotoRepository postPhotoRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.postPhotoRepository = postPhotoRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    //게시글 생성
    public void createPost(PostDto postDto, List<MultipartFile> images) throws IOException {

        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));

        BoardEntity board = boardRepository.findById(postDto.getBoardId())
                .orElseThrow(() -> new NoSuchElementException("게시판을 찾을 수 없습니다."));

        PostEntity newPost = PostEntity.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .views(0)
                .user(user) // Convert postDto.getUserId() to User entity as needed
                .board(board) // Convert postDto.getBoardId() to BoardEntity as needed
                .build();


        postRepository.save(newPost);

        // 2. 이미지 파일 저장
        if (images != null && !images.isEmpty()) {
            for (MultipartFile imageFile : images) {
                if (!imageFile.isEmpty()) {
                    try {
                        String imagePath = saveImageFile(imageFile); // 이미지를 서버에 저장하고 경로 반환

                        // 3. 이미지 경로를 PostPhotoEntity에 저장
                        PostPhotoEntity postPhoto = new PostPhotoEntity(newPost, imagePath);
                        postPhotoRepository.save(postPhoto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private Pageable paging(Integer boardId, int pageNumber, int pageSize, String sortBy, boolean ascending) {
        int page = Math.max(0, pageNumber - 1);

        Pageable pageable = PageRequest.of(page, pageSize,
                Sort.by(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy));
        return pageable;
    }

    //BoardId에 따른 게시글리스트 조회
    public Page<PostDto> getPostsByBoardId(Integer boardId, int pageNumber, int pageSize, String sortBy, boolean ascending) {
        Pageable pageable = paging(boardId, pageNumber, pageSize, sortBy, ascending);

        Page<PostEntity> pageResult = postRepository.findByBoard_BoardId(boardId, pageable);

        return pageResult.map(PostDto::new);
    }


    //최신 업데이트 순 정렬
    public Page<PostDto> getPostsByBoardIdUpdatedAtDesc(Integer boardId, int pageNumber, int pageSize, String sortBy, boolean ascending) {
        Pageable pageable = paging(boardId, pageNumber, pageSize, sortBy, ascending);

        Page<PostEntity> pageResult = postRepository.findByBoard_BoardIdOrderByUpdatedAtDesc(boardId, pageable);

        return pageResult.map(PostDto::new);
    }

    //조회순 정렬
    public Page<PostDto> getPostsByBoardIdViewsDesc(Integer boardId, int pageNumber, int pageSize, String sortBy, boolean ascending) {
        Pageable pageable = paging(boardId, pageNumber, pageSize, sortBy, ascending);

        Page<PostEntity> pageResult = postRepository.findByBoard_BoardIdOrderByViewsDesc(boardId, pageable);

        return pageResult.map(PostDto::new);
    }

    //키워드(글의 내용) 검색을 통한 목록 조회
    public List<PostDto> getPostsBySearch(Integer boardId, String keyword) {
        List<PostEntity> posts = postRepository.searchByContent(boardId, keyword);

        //PostDto 객체를 담을 리스트 생성
        List<PostDto> postDtos = new ArrayList<>();

        // 조회한 게시글 목록 루프 돌면서 postDtos에 추가
        for (PostEntity post : posts) {
            postDtos.add(postMapper.toPostDto(post));
        }

        //변환된 postDtos를 반환
        return postDtos;
    }



    public void incrementViews(Integer postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));

        post.setViews(post.getViews() + 1);
        postRepository.save(post);
    }

    //postId에 따른 게시글 단건 조회
    public PostDto getPostByPostId(Integer postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));

        return postMapper.toPostDto(post);
    }

    //게시글 수정
    public void modifyPost(PostDto postDto) {

        PostEntity post = postRepository.findById(postDto.getPostId())
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));

        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        postRepository.save(post);
    }

    //게시글 삭제
    public void deletePost(Integer postId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("게시글을 찾을 수 없습니다."));

        postRepository.delete(post);
    }

    // 수정 or 삭제하려는 글의 작성자인지 아닌지 체크하는 메서드
    public boolean userCheck(Integer sessionId, Integer userId) {

        if (Objects.equals(sessionId, userId)) {
            return true;
        }
        return false;
    }

    // post service : 특정 BoardId를 가진 게시글들을 일괄 삭제
    // boardId로 모든 게시글을 삭제하는 메서드
    @Transactional
    public void deletePostsByBoardId(Integer boardId) {
        List<PostEntity> posts = postRepository.findByBoard_BoardId(boardId);
        if (!posts.isEmpty()) {
            postRepository.deleteAll(posts);
        }
    }

    private String saveImageFile(MultipartFile imageFile) throws IOException {
        String uploadDir = "src/main/resources/static/uploads/";

        // 파일명 생성
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();

        //파일 경로 생성
        Path filePath = Paths.get(uploadDir, fileName);

        //파일을 해당 경로에 저장
        Files.createDirectories(filePath.getParent()); // 디렉토리가 없다면 생성
        Files.write(filePath, imageFile.getBytes());

        // DB에 저장할 경로 (웹에서 접근 가능한 경로)
        return "/uploads/" + fileName;
    }

}
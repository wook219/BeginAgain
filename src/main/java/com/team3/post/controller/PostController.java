package com.team3.post.controller;


import com.team3.board.BoardEntity;
import com.team3.board.BoardService;
import com.team3.post.entity.PostDto;
import com.team3.post.entity.PostEntity;
import com.team3.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private BoardService boardService;

    //boardId에 따른 게시글 목록 조회
    @GetMapping("/{boardId}")
    public String postList(@PathVariable("boardId") Integer boardId, Model m){
        List<PostDto> posts = postService.getPostsByBoardId(boardId);
        String boardTitle = boardService.getBoard(boardId).getTitle();

        m.addAttribute("posts",posts);
        m.addAttribute("boardId", boardId);
        m.addAttribute("boardTitle", boardTitle);

        return "post/postList";
    }

    //최신 업데이트 순 정렬
    @GetMapping("/{boardId}/updatedAt")
    public String postListOrderByUpdatedAt(@PathVariable("boardId") Integer boardId, Model m){
        List<PostDto> posts = postService.getPostsByBoardIdUpdatedAtDesc(boardId);
        String boardTitle = boardService.getBoard(boardId).getTitle();

        m.addAttribute("posts",posts);
        m.addAttribute("boardId", boardId);
        m.addAttribute("boardTitle", boardTitle);

        return "post/postList";
    }

    //조회수 순 정렬
    @GetMapping("/{boardId}/views")
    public String postListOrderByViewsAt(@PathVariable("boardId") Integer boardId, Model m){
        List<PostDto> posts = postService.getPostsByBoardIdViewsDesc(boardId);
        String boardTitle = boardService.getBoard(boardId).getTitle();

        m.addAttribute("posts",posts);
        m.addAttribute("boardId", boardId);
        m.addAttribute("boardTitle", boardTitle);

        return "post/postList";
    }

    //게시글 번호에 따른 게시글 조회
    @GetMapping("/postdetail/{postId}")
    public String postdetail(@PathVariable("postId") Integer postId,
                             Model m,
                             HttpSession session){
        PostEntity post = postService.getPostByPostId(postId);
        Integer boardId = post.getBoardId();

        Integer userId = postService.getPostByPostId(postId).getUserId();
        if(!postService.userCheck((Integer)session.getAttribute("userId"), userId)) {
            System.out.println("여기 들왕ㅅ");
            m.addAttribute("user_check", "N");
        }

        m.addAttribute("boardId", boardId);
        m.addAttribute("post", post);

        return "post/post";
    }

    //게시판번호에 따른 게시글 작성페이지 이동
    @GetMapping("/create/{boardId}")
    public String createPostForm(@PathVariable("boardId") Integer boardId,
                                 Model m,
                                 RedirectAttributes rattr,
                                 HttpSession session){

        if(session.getAttribute("userId")==null){
            rattr.addFlashAttribute("userSession", "N");
            return "redirect:/login";
        }

        m.addAttribute("boardId", boardId);
        return "post/post_create";
    }

    //게시글 작성
    @PostMapping("/create")
    public String createPost(@RequestParam("boardId") Integer boardId,
                                PostDto postDto,
                             HttpSession session){

        //로그인한 사용자의 세션을 postDto에 set
        Integer userId = (Integer)session.getAttribute("userId");
        postDto.setUserId(userId);

        postDto.setBoardId(boardId);

        postService.createPost(postDto);

        return "redirect:/post/" + boardId;
    }

    //게시글 번호에 따른 게시글 수정 페이지
    @GetMapping("/modify/{postId}")
    public String modifyForm(@PathVariable("postId") Integer postId,
                             Model m,
                             RedirectAttributes rattr,
                             HttpSession session){

        Integer userId = postService.getPostByPostId(postId).getUserId();

        //글 작성자가 맞으면 수정페이지로 이동, 아니면 메시지 띄운 후 다시 게시글 페이지로
        if(postService.userCheck((Integer)session.getAttribute("userId"), userId)){
            PostEntity post = postService.getPostByPostId(postId);

            m.addAttribute("post", post);
            return "post/post_modify";
        };

        rattr.addFlashAttribute("user_check", "N");
        return "redirect:/post/postdetail/" + postId;
    }

    //게시글 번호에 따른 게시글 수정
    @PostMapping("/modify/{postId}")
    public String modifyPost(@PathVariable("postId") Integer postId,
                             PostDto postDto){

        postService.modifyPost(postDto);

        return "redirect:/post/postdetail/"+postId;
    }

    //게시글 번호에 따른 게시글 삭제
    @PostMapping("/delete/{postId}")
    public String deletePost(@PathVariable("postId") Integer postId,
                             @RequestParam("boardId") Integer boardId,
                             RedirectAttributes rattr,
                             Model m,
                             HttpSession session){

        Integer userId = postService.getPostByPostId(postId).getUserId();

        //글 작성자가 맞으면 삭제 반영, 아니면 메시지 띄운 후 다시 게시글 페이지로
        if(postService.userCheck((Integer)session.getAttribute("userId"), userId)){
            postService.deletePost(postId);

            return "redirect:/post/" + boardId;
        };

        return "redirect:/post/postdetail/"+postId;
    }
}

package com.team3.comment.controller;

import com.team3.comment.entity.Comment;
import com.team3.comment.entity.CommentDto;
import com.team3.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; //Spring Framework에서 Model 인터페이스를 사용하기 위해 필요한 import 문. 이 Model 인터페이스는 컨트롤러에서 데이터를 뷰(View)에 전달하는 데 사용.
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired //Spring이 'CommentService'를 자동으로 주입하도록 지정
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // GET /comments 댓글 목록 조회
    @GetMapping
    public String getAllComments(Model model) { // 모든 댓글 조회하여 모델이 추가, comment/listComments 뷰 반환
        List<Comment> comments = commentService.getAllComments();
        model.addAttribute("comments", comments); //조회한 댓글 목록을 Model에 추가하여 뷰에 전달
        return "comment/listComments";  // 댓글 목록 페이지로 이동
    }

    // GET /comments/{id} 특정 댓글 조회
    @GetMapping("/{id}") //{id}가 포함된 경로의 HTTP GET 요청을 처리
    public String getCommentById(@PathVariable("id") Integer id, Model model) { //URL 경로에서 id 값을 추출하여 변수로 사용
        try {
            Comment comment = commentService.getCommentById(id); //주어진 id에 해당하는 댓글을 조회하여 모델에 추가, comment/viewComment 뷰를 반환. 댓글이 없으면 404 페이지로 이동.
            model.addAttribute("comment", comment); //서비스에서 가져온 댓글 리스트(comments)를 Model에 추가, "comments"라는 이름으로 뷰에 전달.
            return "comment/viewComment";  // 댓글 상세 페이지로 이동
        } catch (NoSuchElementException e) {
            return "error/404";  // 댓글이 없을 때 404 페이지로 이동
        }
    }

    // GET /comments/create 댓글 작성 폼 보여주기
    @GetMapping("/create") ///create 경로의 HTTP GET 요청을 처리하여, 댓글 작성 폼을 보여줌
    public String showCreateCommentForm() { //댓글 작성 폼 페이지인 comment/createComment 뷰를 반환.
        return "comment/createComment";  // 댓글 작성 폼 페이지로 이동
    }

    // POST /comments/create 댓글 생성 (DB저장)
    @PostMapping("/create") ///create 경로의 HTTP POST 요청을 처리하여 새로운 댓글을 추가.
    public String addComment(CommentDto commentDto) { //폼 데이터에서 content와 author 값을 추출하여 변수로 사용.
        Comment newComment = commentService.addComment(commentDto); //새로운 댓글을 생성하고, 댓글 목록 페이지로 리다이렉트.
        return "redirect:/comments";  // 댓글 목록 페이지로 리다이렉트
    }

    // GET /comments/edit/{id} 댓글 수정 폼 보여주기
    @GetMapping("/edit/{id}") //{id}가 포함된 경로의 HTTP GET 요청을 처리하여 댓글 수정 폼을 보여줌.
    public String showEditCommentForm(@PathVariable("id") Integer id, Model model) { //주어진 id에 해당하는 댓글을 조회하여 모델에 추가하고, comment/editComment 뷰를 반환. 댓글이 없으면 404 페이지로 이동.
        try {
            Comment comment = commentService.getCommentById(id);
            model.addAttribute("comment", comment);
            return "comment/editComment";  // 댓글 수정 폼 페이지로 이동
        } catch (NoSuchElementException e) {
            return "error/404";  // 댓글이 없을 때 404 페이지로 이동
        }
    }

    // POST /comments/edit/{id} 댓글 수정
    @PostMapping("/edit/{id}") //{id}가 포함된 경로의 HTTP POST 요청을 처리하여, 해당 댓글을 수정.
    public String updateComment(@PathVariable("id") Integer id, //댓글을 수정하고, 댓글 목록 페이지로 리다이렉트. 댓글이 없으면 404 페이지로 이동.
                                @RequestParam("content") String content) {
        try {
            commentService.updateComment(id, content);
            return "redirect:/comments";  // 댓글 목록 페이지로 리다이렉트
        } catch (NoSuchElementException e) {
            return "error/404";  // 댓글이 없을 때 404 페이지로 이동
        }
    }

    // POST /comments/delete/{id} 댓글 삭제(DB저장)
    @PostMapping("/delete/{id}") //{id}가 포함된 경로의 HTTP POST 요청을 처리하여, 해당 댓글을 삭제.
    public String deleteComment(@PathVariable("id") Integer id) { //댓글을 삭제하고, 댓글 목록 페이지로 리다이렉트. 댓글이 없으면 404 페이지로 이동.
        try {
            commentService.deleteComment(id);
            return "redirect:/comments";  // 댓글 목록 페이지로 리다이렉트
        } catch (NoSuchElementException e) {
            return "error/404";  // 댓글이 없을 때 404 페이지로 이동
        }
    }
}

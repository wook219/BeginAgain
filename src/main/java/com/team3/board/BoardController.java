package com.team3.board;

import jakarta.servlet.http.HttpSession;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    // GET -> 목록
    //모든 게시판 조회 후 목록 페이지로 이동
    @GetMapping("")
    public String getAllBoards(Model model) { //controller 와 view 사이에서 데이터를 주고받을 수 있는 데이터 꾸러미 = model
        List<BoardEntity> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/listBoards";  // listBoards.html로 이동
    }

    // GET {id} -> 조회
    // ID로 게시판 조회(수정, 삭제 기능) 후 상세 페이지로 이동
    @GetMapping("/{id}")
    public String getBoard(@PathVariable("id") Integer id, Model model) {
        BoardEntity board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/viewBoardEntity";  // viewBoardEntity.html로 이동
    }


//    // GET {id} -> 조회
//    // ID로 게시판 조회 후 게시글 상세페이지로 이동
//    @GetMapping("/{id}/posts")
//    public String getPostsByBoard(@PathVariable("id") Integer id, Model model) {
//        try {
//            BoardEntity board = boardService.getBoard(id);
//            List<PostEntity> posts = board.getPosts(); // 게시판에 속한 게시글들 조회
//            model.addAttribute("board", board);
//            model.addAttribute("posts", posts);
//            return "board/listPosts";  // 게시글 목록 페이지로 이동
//        } catch (NoSuchElementException e) {
//            return "error/404";  // 게시판이 없을 때 404 페이지로 이동
//        }
//    }


    // GET create -> 작성
    //Create form 페이지로 이동
    @GetMapping("/create")
    public String showCreateBoardForm(HttpSession session, Model model) {

        // 로그인한 사용자의 세션에서 userId 가져오기
        Integer userId = (Integer) session.getAttribute("userId");

        // 만약 userId가 null이면 로그인 페이지로 리다이렉트
        if (userId == null) {
            return "redirect:/login";
        }

        // 필요한 경우 모델에 사용자 정보를 추가
        model.addAttribute("userId", userId);

        return "board/createBoardEntity";  // createTestEntity.html로 이동
    }

    // POST create -> 작성(DB 저장)
    //Create 작성(DB저장)
    /*
    @PostMapping("/create")
    public String createEntity(@RequestParam("title") String title,
                                   @RequestParam("content") String content,
                                   @RequestParam("user_id") String input_user_id,
                                   Model model) {

        try {
            int user_id = Integer.parseInt(input_user_id);
            CreateBoardDto createBoardDto = new CreateBoardDto(title, content, user_id);
            boardService.addBoard(createBoardDto);
//            return "redirect:/";  // 저장 후 메인 페이지로 리다이렉트
            return "redirect:/api/board";  // 저장 후 게시글 목록 페이지로 리다이렉트
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid User ID format.");
            return "board/createBoardEntity";  // 에러 시 작성 페이지로 다시 이동

        }
    }

     */

    @PostMapping("/create")
    public ResponseEntity<String> createEntity(@RequestBody CreateBoardDto createBoardDto, HttpSession session) {
        // 세션에서 userId 가져오기
        Integer sessionUserId = (Integer) session.getAttribute("userId");

        // userId가 null인지 체크하고, null일 경우 에러 응답 반환
        if (sessionUserId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in.");
        }

        // createBoardDto에 userId 설정
        createBoardDto.setUserId(sessionUserId);
        boardService.addBoard(createBoardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("redirect:/board");  // 저장 후 게시글 목록 페이지로 리다이렉트
    }


    // GET edit/{id} -> 수정
    // 게시판 수정 폼으로 이동
    @GetMapping("/edit/{id}")
    public String showEditBoardForm(@PathVariable("id") Integer id, Model model) {
        BoardEntity board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/editBoardEntity";  // editBoardEntity.html로 이동
    }

    // POST edit/{id} -> 수정(DB저장)
    // 게시판 수정 처리 후 리다이렉트
    /*
    @PostMapping("/edit/{id}")
    public String updateBoard(@PathVariable("id") Integer id,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content,
                              Model model) {

//        System.out.println("id=" + id + "title=" +  title + "title=" +content);
        UpdateBoardDTO updateBoardDTO = new UpdateBoardDTO(title, content);
        try {
            boardService.updateBoard(id, updateBoardDTO);
            return "redirect:/api/board/" + id;  // 수정 후 해당 게시글 상세 페이지로 리다이렉트
        } catch (NoSuchElementException e) {
            return "error/404";  // 게시글이 없을 때 404 페이지로 이동
        }
    }
     */

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable("id") Integer id,
                                              @RequestBody UpdateBoardDTO updateBoardDTO) {

        boardService.updateBoard(id, updateBoardDTO);
        return ResponseEntity.status(HttpStatus.OK).body("redirect:/board/" + id);  // 수정 후 해당 게시글 상세 페이지로 리다이렉트
    }


    // POST delete/{id} -> 삭제(DB저장)
    // 게시판 삭제 처리 후 리다이렉트

    @PostMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Integer id) {
        boardService.deleteBoard(id);
        return "redirect:/board";  // 삭제 후 게시글 목록 페이지로 리다이렉트

    }



/*
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable("id") Integer id) {
        try {
            boardService.deleteBoard(id);
            return ResponseEntity.status(HttpStatus.OK).body("Board deleted successfully.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the board.");
        }
    }

 */
}

// http method : get post put delete
// 경로(url) + 행위(http method)

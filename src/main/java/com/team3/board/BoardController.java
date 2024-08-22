package com.team3.board;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("api/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {

        this.boardService = boardService;
    }

    //TODO : GET -> 목록
    // 모든 게시판 조회 후 목록 페이지로 이동
    @GetMapping("")
    public String getAllBoards(Model model) {
        List<BoardEntity> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/listBoards";  // listBoards.html로 이동
    }

    //TODO : GET {id} -> 조회
    // ID로 게시판 조회 후 상세 페이지로 이동
    @GetMapping("/{id}")
    public String getBoard(@PathVariable("id") Integer id, Model model) {
        try {
            BoardEntity board = boardService.getBoard(id);
            model.addAttribute("board", board);
            return "board/viewBoardEntity";  // viewBoardEntity.html로 이동
        } catch (NoSuchElementException e) {
            return "error/404";  // 게시글이 없을 때 404 페이지로 이동
        } catch (IllegalStateException e) {
            return "error/410";  // 게시글이 삭제되었을 때 410 페이지로 이동
        }
    }

    //TODO : GET create -> 작성
    //Create form 페이지로 이동
    @GetMapping("/create")
    public String showCreateBoardForm() {

        return "board/createBoardEntity";  // createTestEntity.html로 이동
    }

    //TODO : POST create -> 작성(DB 저장)
    //Create 작성(DB저장)
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

    //TODO : GET edit/{id} -> 수정
    // 게시판 수정 폼으로 이동
    @GetMapping("/edit/{id}")
    public String showEditBoardForm(@PathVariable("id") Integer id, Model model) {
        try {
            BoardEntity board = boardService.getBoard(id);
            model.addAttribute("board", board);
            return "board/editBoardEntity";  // editBoardEntity.html로 이동
        } catch (NoSuchElementException e) {
            return "error/404";  // 게시글이 없을 때 404 페이지로 이동
        }
    }

    //TODO : POST edit/{id} -> 수정(DB저장)
    // 게시판 수정 처리 후 리다이렉트
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

    //TODO : POST delete/{id} -> 삭제(DB저장)
    // 게시판 삭제 처리 후 리다이렉트
    @PostMapping("/delete/{id}")
    public String deleteBoard(@PathVariable("id") Integer id) {
        try {
            boardService.deleteBoard(id);
            return "redirect:/api/board";  // 삭제 후 게시글 목록 페이지로 리다이렉트
        } catch (NoSuchElementException e) {
            return "error/404";  // 게시글이 없을 때 404 페이지로 이동
        }
    }
}

package com.team3.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // Create form 페이지로 이동
    @GetMapping("/create")
    public String showCreateBoardForm() {
        return "board/createBoardEntity";  // createTestEntity.html로 이동
    }

    @PostMapping("/create")
    public String createTestEntity(@RequestParam("title") String title,
                                   @RequestParam("content") String content,
                                   @RequestParam("user_id") String input_user_id,
                                   Model model) {

        int user_id = Integer.parseInt(input_user_id);
        CreateBoardDto createBoardDto = new CreateBoardDto(title, content, user_id);
        boardService.addBoard(createBoardDto);

        return "redirect:/";
    }

}

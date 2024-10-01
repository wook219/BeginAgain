package com.team3.global.controller;

import com.team3.board.BoardEntity;
import com.team3.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    BoardService boardService;

    @GetMapping
    public String home(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "9") int size,
                       @RequestParam(value = "sortField", defaultValue = "createdAt") String sortField,
                       @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        Page<BoardEntity> boards = boardService.getBoards(page, size, sortField, sortDir, keyword);
        model.addAttribute("boards", boards.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boards.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("keyword", keyword); // 검색 키워드를 모델에 추가
        return "board/listBoards";
    }
}

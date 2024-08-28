package com.team3.board;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardUtils boardUtils;

    @Autowired
    public BoardController(BoardService boardService, BoardUtils boardUtils) {
        this.boardService = boardService;
        this.boardUtils = boardUtils;
    }

    // GET -> 목록 : 모든 게시판 조회 후 목록 페이지로 이동
    @GetMapping("")
    public String getAllBoards(Model model) {
        List<BoardEntity> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/listBoards";  // listBoards.html로 이동
    }

    // GET {id} -> 조회 : ID로 게시판 조회 후 상세 페이지로 이동
    @GetMapping("/{id}")
    public String getBoard(@PathVariable("id") Integer id, Model model) {
        BoardEntity board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/viewBoardEntity";  // viewBoardEntity.html로 이동
    }

    // GET create -> 작성 : Create form 페이지로 이동
    @GetMapping("/create")
    public String showCreateBoardForm(HttpSession session, Model model) {

        Integer userId = boardUtils.checkUserSession(session);
        // 만약 userId가 null이면 로그인 페이지로 리다이렉트
        if (userId == null) {
            return "redirect:/login";
        }

        model.addAttribute("userId", userId);
        return "board/createBoardEntity"; // createBoardEntity.html로 이동
    }

    @PostMapping("/create")
    public ResponseEntity<String> createEntity(@RequestBody CreateBoardDto createBoardDto, HttpSession session) {

        Integer sessionUserId = boardUtils.checkUserSession(session);
        // userId가 null인지 체크하고, null일 경우 로그인
        if (sessionUserId == null) {
            return boardUtils.buildRedirectLogin(sessionUserId);
        }

        createBoardDto.setUserId(sessionUserId);
        boardService.addBoard(createBoardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("redirect:/board");
    }

    // GET edit/{id} -> 수정 : 게시판 수정 폼으로 이동
    @GetMapping("/edit/{id}")
    public String showEditBoardForm(@PathVariable("id") Integer id, Model model, HttpSession session) {

        Integer sessionUserId = boardUtils.checkUserSession(session);
        // userId가 null인지 체크하고, null일 경우 로그인
        if (sessionUserId == null) {
            return "redirect:/login";
        }

        BoardEntity board = boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board/editBoardEntity";
    }

    @GetMapping("/edit/validate/{id}")
    public ResponseEntity<String> validateEditBoardForm(@PathVariable("id") Integer id, HttpSession session) {

        Integer sessionUserId = boardUtils.checkUserSession(session);
        // userId가 null인지 체크하고, null일 경우 로그인
        if (sessionUserId == null) {
            return boardUtils.buildRedirectLogin(sessionUserId);
        }

        BoardEntity board = boardService.getBoard(id);
        Integer authorUserId = board.getUser().getId();

        if (!authorUserId.equals(sessionUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유저 정보가 다릅니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("수정페이지로 접근해도 됩니다.");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable("id") Integer id,
                                              @RequestBody UpdateBoardDTO updateBoardDTO,
                                              HttpSession session) {

        Integer sessionUserId = boardUtils.checkUserSession(session);
        // userId가 null인지 체크하고, null일 경우 로그인
        if (sessionUserId == null) {
            return boardUtils.buildRedirectLogin(sessionUserId);
        }

        boardService.updateBoard(id, sessionUserId, updateBoardDTO);
        return ResponseEntity.status(HttpStatus.OK).body("redirect:/board/" + id);
    }

    // DELETE delete/{id} -> 삭제(DB저장)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBoardRest(@PathVariable("id") Integer id, HttpSession session) {

        Integer sessionUserId = boardUtils.checkUserSession(session);
        // userId가 null인지 체크하고, null일 경우 로그인
        if (sessionUserId == null) {
            return boardUtils.buildRedirectLogin(sessionUserId);
        }

        BoardEntity board = boardService.getBoard(id);
        Integer authorUserId = board.getUser().getId();


        boardService.deleteBoard(id, sessionUserId);
        return ResponseEntity.status(HttpStatus.OK).body("Board deleted successfully.");
    }

    /////////////추가기능
    // 최신 생성 순 정렬
    @GetMapping("/createdAtDesc")
    public String getBoardsOrderByCreatedAtDesc(Model model) {
        List<BoardSortDto> boards = boardService.getAllBoardsOrderByCreatedAtDesc();  // 서비스에서 유사한 메서드 호출
        model.addAttribute("boards", boards);
        return "board/listBoards";  // listBoards.html로 이동
    }

    // 오래된 순 정렬
    @GetMapping("/createdAtAsc")
    public String getBoardsOrderByCreatedAtAsc(Model model) {
        List<BoardSortDto> boards = boardService.getAllBoardsOrderByCreatedAtAsc();  // 서비스에서 유사한 메서드 호출
        model.addAttribute("boards", boards);
        return "board/listBoards";  // listBoards.html로 이동
    }

    // 게시판 제목으로 키워드 검색 메서드
    @GetMapping("/search")
    public String searchBoardsByTitle(@RequestParam("keyword") String keyword, Model model) {
        List<BoardSearchDto> boards = boardService.searchBoardsByTitle(keyword);
        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);  // 검색어를 뷰에 전달
        return "board/listBoards";  // listBoards.html로 이동
    }

//    // GET -> 목록 : 모든 게시판 조회 후 목록 페이지로 이동
//    @GetMapping("")
//    public String getAllBoards(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
//        List<BoardEntity> boards;
//        if (keyword != null && !keyword.isEmpty()) {
//            boards = boardService.searchBoardsByTitle(keyword);
//        } else {
//            boards = boardService.getAllBoards();
//        }
//        model.addAttribute("boards", boards);
//        return "board/listBoards";  // listBoards.html로 이동
//    }
}

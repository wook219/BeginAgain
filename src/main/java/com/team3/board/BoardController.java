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
import java.util.Enumeration;
import java.util.List;

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

    // GET create -> 작성
    //Create form 페이지로 이동
    @GetMapping("/create")
    public String showCreateBoardForm(HttpSession session, Model model) {

//        Enumeration<String> attributeNames = session.getAttributeNames();
//        while(attributeNames.hasMoreElements()) {
//            String name = attributeNames.nextElement();
//            Object value = session.getAttribute(name);
//            System.out.println("eleName = " + name + ", value = " +  value);
//        }

        // 로그인한 사용자의 세션에서 userId 가져오기
        Integer userId = (Integer) session.getAttribute("userId");

        // 만약 userId가 null이면 로그인 페이지로 리다이렉트
        if (userId == null) {
            return "redirect:/login";
        }

        // 필요한 경우 모델에 사용자 정보를 추가
        model.addAttribute("userId", userId);

        return "board/createBoardEntity"; // createTestEntity.html로 이동
    }

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
    public String showEditBoardForm(@PathVariable("id") Integer id, Model model, HttpSession session) {

        // 세션에서 userId 가져오기
        Object sessionUserIdValue = session.getAttribute("userId");

        // sessionUserIdValue가 null인지 확인
        if (sessionUserIdValue == null || sessionUserIdValue.toString().isEmpty()) {
            return "redirect:/login";  // 리다이렉트 경로 반환
        }

        // sessionUserIdValue를 Integer로 변환
        Integer sessionUserId = (Integer) sessionUserIdValue;

        BoardEntity board = boardService.getBoard(id);
        Integer authorUserId = board.getUser().getId();

        // 게시글 작성자와 세션 유저 ID가 일치하지 않는 경우
        if (!authorUserId.equals(sessionUserId)) {
            model.addAttribute("error", "유저 정보가 다릅니다.");  // 에러 메시지 모델에 추가
            // TODO : 에러처리에 대한 보완 필요
            return "error/404";  // 리스트로 돌아가게함
        }

        model.addAttribute("board", board);
        return "board/editBoardEntity";  // 뷰 이름 반환
    }


    @GetMapping("/edit/validate/{id}")
    public ResponseEntity<String> validateEditBoardForm(@PathVariable("id") Integer id, Model model, HttpSession session) {

        // 세션에서 userId 가져오기
        Object sessionUserIdValue = session.getAttribute("userId");

        // sessionUserIdValue가 null인지 확인
        if (sessionUserIdValue == null || sessionUserIdValue.toString().isEmpty()) {
            // ResponseEntity로 리다이렉트 수행
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/login"));  // 리다이렉트할 경로 설정
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
        }

        // sessionUserIdValue를 Integer로 변환
        Integer sessionUserId = (Integer) sessionUserIdValue;

        BoardEntity board = boardService.getBoard(id);
        Integer authorUserId = board.getUser().getId();

        // 게시글 작성자와 세션 유저 ID가 일치하지 않는 경우
        if (!authorUserId.equals(sessionUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유저 정보가 다릅니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("수정페이지로 접근해도돼");
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable("id") Integer id,
                                              @RequestBody UpdateBoardDTO updateBoardDTO,
                                              HttpSession session) {

        // 세션에서 userId 가져오기
        Object sessionUserIdValue = session.getAttribute("userId");

        // sessionUserIdValue가 null인지 확인
        if (sessionUserIdValue == null || sessionUserIdValue.toString().isEmpty()) {
            // ResponseEntity로 리다이렉트 수행
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/login"));  // 리다이렉트할 경로 설정
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
        }

        // sessionUserIdValue를 Integer로 변환
        Integer sessionUserId = (Integer) sessionUserIdValue;

        BoardEntity board = boardService.getBoard(id);
        Integer authorUserId = board.getUser().getId();

        // 게시글 작성자와 세션 유저 ID가 일치하지 않는 경우
        if (!authorUserId.equals(sessionUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유저 정보가 다릅니다.");
        }

        boardService.updateBoard(id, updateBoardDTO);
        return ResponseEntity.status(HttpStatus.OK).body("redirect:/board/" + id);  // 수정 후 해당 게시글 상세 페이지로 리다이렉트
    }

    // DELETE delete/{id} -> 삭제(DB저장)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBoardRest(@PathVariable("id") Integer id, HttpSession session) {

        // 세션에서 userId 가져오기
        Object sessionUserIdValue = session.getAttribute("userId");

        // sessionUserIdValue가 null인지 확인
        if (sessionUserIdValue == null || sessionUserIdValue.toString().isEmpty()) {
            // ResponseEntity로 리다이렉트 수행
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create("/login"));  // 리다이렉트할 경로 설정
            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).build();
        }

        // sessionUserIdValue를 Integer로 변환
        Integer sessionUserId = (Integer) sessionUserIdValue;

        BoardEntity board = boardService.getBoard(id);
        Integer authorUserId = board.getUser().getId();

        // 게시글 작성자와 세션 유저 ID가 일치하지 않는 경우
        if (!authorUserId.equals(sessionUserId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유저 정보가 다릅니다.");
        }

        boardService.deleteBoard(id);
        return ResponseEntity.status(HttpStatus.OK).body("Board deleted successfully.");
    }
}

// http method : get post put delete
// 경로(url) + 행위(http method)

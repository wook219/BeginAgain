package com.team3.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시판 생성
    public BoardEntity addBoard(CreateBoardDto createBoardDto){

        BoardEntity newBoard = new BoardEntity(
                null, // id는 자동 생성
                createBoardDto.getTitle(),
                createBoardDto.getContent(),
                createBoardDto.getUserId(),
                LocalDateTime.now(), // 생성일자 설정
                null, // 수정일자는 처음에는 null로 설정
                null,  // 삭제일자는 처음에는 null로 설정
                false
        );

        boardRepository.save(newBoard);

        return newBoard;
    }



    // TODO : 게시판 상세 조회
    // TODO : 게시판 전체 (리스트) 조회
    // TODO : 게시판 수정
    // TODO : 게시판 삭제
}

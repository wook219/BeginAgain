package com.team3.board;

import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.support.CustomSQLExceptionTranslatorRegistrar;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    // 게시판 상세 조회
    public BoardEntity getPost(Integer boardId) {
        // 기존 게시판 조회
        BoardEntity existingBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("게시판을 찾을 수 없습니다."));

        // 기존 게시판이 삭제되었는지 확인
        if (existingBoard.isDeleted()) {
            // 삭제된 게시글이면 예외를 던집니다.
            throw new IllegalStateException("게시판이 삭제되었습니다.");
        } else {
            // 삭제되지 않은 게시판이면 해당 게시글 반환
            return existingBoard;
        }
    }


//    // 삭제되지 않은 게시판 조회 및 페이지네이션
//    public Page<BoardEntity> getAllBoards(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
//        return boardRepository.findByDeletedAtIsNull(pageable);
//    }


    // TODO : 게시판 전체 (리스트) 조회
    // 삭제되지 않은 모든 게시판 조회
    public List<BoardEntity> getAllBoards() {
        return boardRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }


    // TODO : 게시판 수정
    public BoardEntity updateBoard(Integer boardId, UpdateBoardDTO updateBoardDTO) {
        // TODO : 현재 접속 중인 사용자 정보 조회
//        UserDocument currentUser = userService.getCurrentUser();

        // 기존 게시판 조회
        BoardEntity existingBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("게시판을 찾을 수 없습니다."));

        // TODO : 현재 사용자가 작성자인지 확인
//        if (! existingBoard.getAuthor().getId().equals(currentUser.getId())) {
//            throw new CustomException(USER_NOT_AUTHENTICATED);
//        }

        // 업데이트할 필드만 설정
        if (updateBoardDTO.getTitle() != null) {
            existingBoard.setTitle(updateBoardDTO.getTitle());
        }
        if (updateBoardDTO.getContent() != null) {
            existingBoard.setContent(updateBoardDTO.getContent());
        }

        // 수정일자 업데이트
        existingBoard.setUpdatedAt(LocalDateTime.now());

        // 수정된 게시판 저장
        return boardRepository.save(existingBoard);

    }

    // TODO : 게시판 삭제
    public BoardEntity deleteBoard(Integer boardId) {
        // TODO : 현재 접속 중인 사용자 정보 조회

        // 기존 게시판 조회
        BoardEntity existingBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("게시판을 찾을 수 없습니다."));


        // TODO : 현재 사용자가 작성자인지 확인
//        if (!existingBoard.getAuthor().getId().equals(currnetUser.getId())) {
//            throw new CustomException(USER_NOT_AUTHENTICATED);
//        }

        // 삭제일자 업데이트
        existingBoard.setDeletedAt(LocalDateTime.now());

        return boardRepository.save(existingBoard);
    }

}

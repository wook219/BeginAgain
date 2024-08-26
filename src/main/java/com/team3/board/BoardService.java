package com.team3.board;

import com.team3.user.entity.User;
import com.team3.user.repository.UserRepository;
import com.team3.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;  // UserService 대신 UserRepository 사용


    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    // 게시판 생성
    public BoardEntity addBoard(CreateBoardDto createBoardDto) {
        // UserRepository를 사용하여 User 엔티티를 조회
        User user = userRepository.findById(createBoardDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다."));

        // 새로운 BoardEntity 생성
        BoardEntity newBoard = BoardEntity.builder()
                .title(createBoardDto.getTitle())
                .content(createBoardDto.getContent())
                .user(user) // 연관된 User 엔티티 설정
                .createdAt(LocalDateTime.now()) // 생성일자 설정
                .isDeleted(false) // 삭제 여부 false로 설정
                .build();

        boardRepository.save(newBoard);
        return newBoard;
    }

    // 반복적인 게시판 조회 코드를 줄이기 위해 공통 메서드 추가
    private BoardEntity findExistingBoard(Integer boardId) {
        BoardEntity board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("게시판을 찾을 수 없습니다."));

        if (board.isDeleted()) {
            throw new IllegalStateException("게시판이 삭제되었습니다.");
        }

        return board;
    }

    // 게시판 상세 조회
    public BoardEntity getBoard(Integer boardId) {
        return findExistingBoard(boardId);
    }

    // 게시판 전체 (리스트) 조회
    public List<BoardEntity> getAllBoards() {
        return boardRepository.findByDeletedAtIsNullOrderByCreatedAtDesc();
    }

    // 게시판 수정
    public BoardEntity updateBoard(Integer boardId, UpdateBoardDTO updateBoardDTO) {
        // TODO : 현재 접속 중인 사용자 정보 조회
//        UserDocument currentUser = userService.getCurrentUser();

        // 공통 메서드를 사용하여 기존 게시판 조회
        BoardEntity existingBoard = findExistingBoard(boardId);

        // TODO : 현재 사용자가 작성자인지 확인
//        if (!existingBoard.getAuthor().getId().equals(currentUser.getId())) {
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

    // 게시판 삭제
    public BoardEntity deleteBoard(Integer boardId) {
        // TODO : 현재 접속 중인 사용자 정보 조회

        // 공통 메서드를 사용하여 기존 게시판 조회
        BoardEntity existingBoard = findExistingBoard(boardId);

        // TODO : 현재 사용자가 작성자인지 확인
//        if (!existingBoard.getAuthor().getId().equals(currentUser.getId())) {
//            throw new CustomException(USER_NOT_AUTHENTICATED);
//        }

        // 삭제일자 업데이트
        existingBoard.setDeletedAt(LocalDateTime.now());

        return boardRepository.save(existingBoard);
    }
}

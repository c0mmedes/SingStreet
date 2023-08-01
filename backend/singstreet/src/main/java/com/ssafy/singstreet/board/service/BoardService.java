package com.ssafy.singstreet.board.service;

import com.ssafy.singstreet.board.db.entity.Board;
import com.ssafy.singstreet.board.db.repo.BoardCommentRepository;
import com.ssafy.singstreet.board.db.repo.BoardRepository;
import com.ssafy.singstreet.board.model.BoardRequestDto;
import com.ssafy.singstreet.board.model.BoardUpdateRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardCommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    // Save
    public Boolean saveBoard(BoardRequestDto requestDto){
        Board board = Board.builder()
                .user(userRepository.findByUserId(requestDto.getUserId()))
                .title(requestDto.getTitle())
                .type(requestDto.getType())
                .content(requestDto.getContent())
                .build();

        boardRepository.save(board);
        return true;
    }

    @Transactional
    // Update
    public Boolean updateBoard(BoardUpdateRequestDto requestDto){
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다."+requestDto.getBoardId()));

        board.update(requestDto.getTitle(), requestDto.getContent());
        boardRepository.save(board);
        return true;
    }

    @Transactional
    // Delete
    public Boolean deleteBoard(int boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글 없습니다. : "+ boardId));

        board.delete();
        boardRepository.save(board);
        return true;
    }
}

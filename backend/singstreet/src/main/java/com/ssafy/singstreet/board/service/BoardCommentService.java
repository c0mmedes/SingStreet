package com.ssafy.singstreet.board.service;

import com.ssafy.singstreet.board.db.entity.BoardComment;
import com.ssafy.singstreet.board.db.repo.BoardCommentRepository;
import com.ssafy.singstreet.board.db.repo.BoardRepository;
import com.ssafy.singstreet.board.model.comment.BoardCommentRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardCommentService {
    private final BoardCommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public boolean createBoardComment(BoardCommentRequestDto requestDto){
        BoardComment comment = BoardComment.builder()
                .user(userRepository.findByUserId(requestDto.getUserId()))
                .board(boardRepository.findByBoardId(requestDto.getBoardId()))
                .content(requestDto.getContent())
                .build();

        commentRepository.save(comment);
        return true;
    }
}

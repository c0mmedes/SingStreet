package com.ssafy.singstreet.board.service;

import com.ssafy.singstreet.board.db.entity.Board;
import com.ssafy.singstreet.board.db.entity.BoardComment;
import com.ssafy.singstreet.board.db.repo.BoardCommentRepository;
import com.ssafy.singstreet.board.db.repo.BoardRepository;
import com.ssafy.singstreet.board.model.comment.BoardCommentRequestDto;
import com.ssafy.singstreet.board.model.comment.BoardCommentResponseDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardCommentService {
    private final BoardCommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    // Read BoardComment
    public List<BoardCommentResponseDto> readBoardComments(int boardId){
        Board board = boardRepository.findById(boardId).orElseThrow(()->
                new IllegalArgumentException("해당 게시물이 없습니다. boardId : "+boardId));

        List<BoardComment> commentList = commentRepository.findBoardCommentByBoard(board);
        return commentList.stream().map(this::convertCommentToDto).collect(Collectors.toList());
    }

    // Create BoardComment
    public boolean createBoardComment(BoardCommentRequestDto requestDto){
        BoardComment comment = BoardComment.builder()
                .user(userRepository.findByUserId(requestDto.getUserId()))
                .board(boardRepository.findByBoardId(requestDto.getBoardId()))
                .content(requestDto.getContent())
                .build();

        commentRepository.save(comment);
        return true;
    }

    // Update BoardComment
    public boolean deleteBoardComment(int commentId){
        commentRepository.findById(commentId).orElseThrow(()->
                new IllegalArgumentException("해당 댓글이 없습니다. commentId" + commentId));

        commentRepository.deleteById(commentId);
        return true;
    }



    // Convert
    public BoardCommentResponseDto convertCommentToDto(BoardComment comment){
        return BoardCommentResponseDto.builder()
                .userId(comment.getUser().getUserId())
                .userImg(comment.getUser().getUserImg())
                .nickname(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}

package com.ssafy.singstreet.board.controller;

import com.ssafy.singstreet.board.model.comment.BoardCommentRequestDto;
import com.ssafy.singstreet.board.model.comment.BoardCommentResponseDto;
import com.ssafy.singstreet.board.service.BoardCommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardCommentApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BoardCommentService commentService;

    @GetMapping("board/comment/{boardId}")
    public ResponseEntity<List<BoardCommentResponseDto>> readBoardComments(@PathVariable int boardId){
        log.debug("[Read BoardComments] boardId : ", boardId);

        return new ResponseEntity(commentService.readBoardComments(boardId),HttpStatus.OK);
    }

    @PostMapping("/board/comment")
    public ResponseEntity<Boolean> createBoardComment(@RequestBody BoardCommentRequestDto requestDto){
        log.debug("[Create BoardComment] requestDto", requestDto);

        return new ResponseEntity(commentService.createBoardComment(requestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/board/comment/{commentId}")
    public ResponseEntity<Boolean> updateBoardComment(@PathVariable int commentId){
        log.debug("[Update BoardComment] commentId :" , commentId);

        return new ResponseEntity(commentService.deleteBoardComment(commentId), HttpStatus.OK);
    }

}

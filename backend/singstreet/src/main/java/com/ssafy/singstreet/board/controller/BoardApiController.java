package com.ssafy.singstreet.board.controller;

import com.ssafy.singstreet.board.model.BoardRequestDto;
import com.ssafy.singstreet.board.model.BoardUpdateRequestDto;
import com.ssafy.singstreet.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BoardService boardService;


    // Create Board
    @PostMapping("/board")
    public ResponseEntity<Boolean> createBoard(@RequestBody BoardRequestDto requestDto){
        log.debug("[Create Board(User)] requestDot", requestDto);

        return new ResponseEntity(boardService.saveBoard(requestDto), HttpStatus.CREATED );
    }

    //Update Board
    @PutMapping("/board")
    public ResponseEntity<Boolean> updateBoard(@RequestBody BoardUpdateRequestDto requestDto){
        log.debug("[Update Board(User)] requestDto : ", requestDto);

        return new ResponseEntity(boardService.updateBoard(requestDto),HttpStatus.OK);
    }

    //Delete Board
    @PutMapping("/board/delete/{boardId}")
    public ResponseEntity<Boolean> deleteBoard(@PathVariable int boardId){
        log.debug("[Delete Board(User)] boardId : ",boardId);

        return new ResponseEntity(boardService.deleteBoard(boardId),HttpStatus.OK);
    }

}

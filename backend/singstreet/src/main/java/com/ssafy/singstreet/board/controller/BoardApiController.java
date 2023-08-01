package com.ssafy.singstreet.board.controller;

import com.ssafy.singstreet.board.model.BoardRequestDto;
import com.ssafy.singstreet.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BoardService boardService;

    @PostMapping("/board")
    public ResponseEntity<Boolean> createBoard(@RequestBody BoardRequestDto requestDto){
        log.debug("[Create Board(User)] requestDot", requestDto);

        return new ResponseEntity(boardService.saveBoard(requestDto), HttpStatus.CREATED );
    }


}

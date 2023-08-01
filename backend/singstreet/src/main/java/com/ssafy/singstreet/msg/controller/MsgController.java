package com.ssafy.singstreet.msg.controller;

import com.ssafy.singstreet.msg.model.MsgRequestDto;
import com.ssafy.singstreet.msg.service.MsgService;
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
public class MsgController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MsgService msgService;

    @PostMapping("/msg")
    public ResponseEntity<Boolean> createMsg(@RequestBody MsgRequestDto requestDto){
        log.debug("[Create Msg] requestDto : ",requestDto);

        return new ResponseEntity(msgService.createMsg(requestDto), HttpStatus.CREATED);
    }
}

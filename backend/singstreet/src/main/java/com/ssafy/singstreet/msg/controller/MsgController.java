package com.ssafy.singstreet.msg.controller;

import com.ssafy.singstreet.msg.model.MsgDetailResponseDto;
import com.ssafy.singstreet.msg.model.MsgRequestDto;
import com.ssafy.singstreet.msg.model.MsgResponseDto;
import com.ssafy.singstreet.msg.service.MsgService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MsgController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MsgService msgService;

    // Read Msg
    // - Receiver ------------
    //받은 읽은 쪽지 조회
    @GetMapping("/msg/receive/confirmed/{userId}")
    public ResponseEntity<List<MsgResponseDto>> readReceiveConfirmedMsgList(@PathVariable int userId){
        log.debug("[Read ConfirmedReceiveMsgList] userId :" ,userId);

        return new ResponseEntity(msgService.readReceiveConfirmedMsgList(userId),HttpStatus.OK);
    }
    //받은 '안' 읽은 쪽지 조회
    @GetMapping("/msg/receive/{userId}")
    public ResponseEntity<List<MsgResponseDto>> readReceiveNotConfirmedMsgList(@PathVariable int userId){
        log.debug("[Read NotConfirmedReceiveMsgList] userId :" ,userId);

        return new ResponseEntity(msgService.readReceiveNotConfirmedMsgList(userId),HttpStatus.OK);
    }

    // - Sender -----------
    @GetMapping("/msg/send/{userId}")
    public ResponseEntity<List<MsgResponseDto>> readSendMsgList(@PathVariable int userId){
        log.debug("[Read SendMsgList] userId :" ,userId);

        return new ResponseEntity(msgService.readSendMsgList(userId),HttpStatus.OK);
    }


    // Read Detail
    @GetMapping("/msg/detail/{msgId}")
    public ResponseEntity<MsgDetailResponseDto> readDetail(@PathVariable int msgId){
        log.debug("[Read Detail] msgId :" ,msgId);

        return new ResponseEntity(msgService.readDetail(msgId),HttpStatus.OK);
    }


    // Create Msg
    @PostMapping("/msg")
    public ResponseEntity<Boolean> createMsg(@RequestBody MsgRequestDto requestDto){
        log.debug("[Create Msg] requestDto : ",requestDto);

        return new ResponseEntity(msgService.createMsg(requestDto), HttpStatus.CREATED);
    }
}

package com.ssafy.singstreet.msg.controller;

import com.ssafy.singstreet.msg.model.MsgDetailResponseDto;
import com.ssafy.singstreet.msg.model.MsgRequestDto;
import com.ssafy.singstreet.msg.model.MsgResponseDto;
import com.ssafy.singstreet.msg.service.MsgService;
import com.ssafy.singstreet.user.service.UserService;
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
    private final UserService userService;

    // Read Msg
    // - Receiver ------------
    //받은 읽은 쪽지 조회
    @GetMapping("/msg/receive/confirmed")
    public ResponseEntity<List<MsgResponseDto>> readReceiveConfirmedMsgList(){
        int userId = userService.getCurrentUserId();
        log.debug("[Read ConfirmedReceiveMsgList] userId :" ,userId);

        return new ResponseEntity(msgService.readReceiveConfirmedMsgList(userId),HttpStatus.OK);
    }
    //받은 '안' 읽은 쪽지 조회
    @GetMapping("/msg/receive")
    public ResponseEntity<List<MsgResponseDto>> readReceiveNotConfirmedMsgList(){
        int userId = userService.getCurrentUserId();
        log.debug("[Read NotConfirmedReceiveMsgList] userId :" ,userId);

        return new ResponseEntity(msgService.readReceiveNotConfirmedMsgList(userId),HttpStatus.OK);
    }

    // - Sender -----------
    @GetMapping("/msg/send")
    public ResponseEntity<List<MsgResponseDto>> readSendMsgList(){
        int userId = userService.getCurrentUserId();
        log.debug("[Read SendMsgList] userId :" ,userId);

        return new ResponseEntity(msgService.readSendMsgList(userId),HttpStatus.OK);
    }


    // Read Detail
    @GetMapping("/msg/detail/{msgId}")
    public ResponseEntity<MsgDetailResponseDto> readDetail(@PathVariable int msgId){
        log.debug("[Read Detail] msgId :" ,msgId);

        return new ResponseEntity(msgService.readDetail(msgId),HttpStatus.OK);
    }


    //Delete Msg -> 리펙토링 필요 어떻게 가져올지 고민....이렇게 넣어도 됨?
    @PutMapping("/msg/delete/{msgId}")
    public ResponseEntity<Boolean> delete(@PathVariable int msgId){
        int userId = userService.getCurrentUserId();
        return new ResponseEntity(msgService.DeleteMsg(userId, msgId), HttpStatus.OK);
    }

    // Create Msg
    @PostMapping("/msg")
    public ResponseEntity<Boolean> createMsg(@RequestBody MsgRequestDto requestDto){
        int receiver = userService.getCurrentUserId();
        requestDto.updateReceiver(receiver);
        log.debug("[Create Msg] requestDto : ",requestDto);

        return new ResponseEntity(msgService.createMsg(requestDto), HttpStatus.CREATED);
    }
}

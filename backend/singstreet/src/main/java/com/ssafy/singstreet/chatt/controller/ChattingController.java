//package com.ssafy.singstreet.chatt.controller;
//
//import com.ssafy.singstreet.chatt.db.ChatMessage;
//import com.ssafy.singstreet.chatt.service.ChatMessageService;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.data.domain.Slice;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//public class ChattingController {
//    private final Logger log = LoggerFactory.getLogger(getClass());
//
//    private final ChatMessageService messageService;
//
//    @GetMapping("/chatting/{entId}")
//    public ResponseEntity<Slice<ChatMessage>> read(@PathVariable int entId, @RequestParam int page, @RequestParam int size){
//        log.debug("[readChattPage]");
//
//        return new ResponseEntity(messageService.getMessagesWithPagination(entId,page,size), HttpStatus.OK);
//    }
//
//}

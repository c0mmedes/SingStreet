package com.example.demo.chatt.controller;

import com.example.demo.chatt.db.ChatMessage;
import com.example.demo.chatt.service.ChatMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChattingController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ChatMessageService messageService;

    // 생성자 추가
    public ChattingController(ChatMessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/chatting/{entId}")
    public ResponseEntity<Slice<ChatMessage>> read(@PathVariable int entId, @RequestParam int page, @RequestParam int size) {
        log.debug("[readChattPage]");

        return new ResponseEntity(messageService.getMessagesWithPagination(entId, page, size), HttpStatus.OK);
    }
}

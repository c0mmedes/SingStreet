package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedSaveRequestDto;
import com.ssafy.singstreet.ent.service.EntFeedService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor //final생성자 자동생성
@RestController
public class EntFeedApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final EntFeedService feedService;

    @PostMapping("/ent/feed")
    public ResponseEntity<Boolean> create(@RequestBody EntFeedSaveRequestDto requestDto){
        log.debug("[entFeedCreate] EntFeedSaveRequestDto :", requestDto);

        return new ResponseEntity(feedService.saveFeed(requestDto), HttpStatus.CREATED);
    }
}

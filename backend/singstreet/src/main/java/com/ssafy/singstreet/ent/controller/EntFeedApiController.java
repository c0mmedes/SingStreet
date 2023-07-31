package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedLikeRequestDto;
import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedResponseDto;
import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedCreateRequestDto;
import com.ssafy.singstreet.ent.model.entFeedDto.EntFeedUpdateRequestDto;
import com.ssafy.singstreet.ent.service.EntFeedService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor //final생성자 자동생성
@RestController
public class EntFeedApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final EntFeedService feedService;

    // feed Read--------------------------
    @GetMapping("/ent/feed/{entId}")
    public ResponseEntity<EntFeedResponseDto> readAll(@PathVariable int entId, @RequestParam int page, @RequestParam int size){
        log.debug("[EntFeed readALL] endId :",entId);

        return new ResponseEntity(feedService.readAll(entId,page,size),HttpStatus.OK);
    }

    @GetMapping("/ent/feed/notice/{entId}")
    public ResponseEntity<EntFeedResponseDto> readNotice (@PathVariable int entId, @RequestParam int page, @RequestParam int size){
        log.debug("[EntFeed readNotice] endId :",entId);

        return new ResponseEntity(feedService.readNotice(entId, page, size),HttpStatus.OK);
    }

    @GetMapping("/ent/feed/common/{entId}")
    public ResponseEntity<EntFeedResponseDto> readCommon (@PathVariable int entId, @RequestParam int page, @RequestParam int size){
        log.debug("[EntFeed readNotice] endId :",entId);

        return new ResponseEntity(feedService.readCommon(entId, page, size),HttpStatus.OK);
    }


    // feed create -------------------------
    @PostMapping("/ent/feed")
    public ResponseEntity<Boolean> create(@RequestBody EntFeedCreateRequestDto requestDto){
        log.debug("[entFeedCreate] EntFeedSaveRequestDto :", requestDto);

        return new ResponseEntity(feedService.saveFeed(requestDto), HttpStatus.CREATED);
    }



    // feed update -------------------------
    @PutMapping("/ent/feed")
    public ResponseEntity<Boolean> update(@RequestBody EntFeedUpdateRequestDto requestDto){
        log.debug("[entFeed Update] EntFeedUpdateRequestDto : ", requestDto);
        return new ResponseEntity(feedService.updateFeed(requestDto), HttpStatus.OK);
    }


    // feed delete -------------------------




    // like -------------------------------
    @PostMapping("/ent/feed/like")
    public ResponseEntity<String> like(@RequestBody EntFeedLikeRequestDto requestDto){
        log.debug("[entFeed] EntFeedLikeRequestDto : ",requestDto);

        return new ResponseEntity(feedService.like(requestDto),HttpStatus.OK);
    }

}

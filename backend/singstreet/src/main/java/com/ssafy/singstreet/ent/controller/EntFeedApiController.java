package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.entFeedCommentDto.EntFeedCommentRequestDto;
import com.ssafy.singstreet.ent.model.entFeedCommentDto.EntFeedCommentResponseDto;
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

    // Feed -------------------------------------------------------------
    // feed Read
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
    // feed Create
    @PostMapping("/ent/feed")
    public ResponseEntity<Boolean> createFeed(@RequestBody EntFeedCreateRequestDto requestDto){
        log.debug("[entFeedCreate] EntFeedSaveRequestDto :", requestDto);

        return new ResponseEntity(feedService.saveFeed(requestDto), HttpStatus.CREATED);
    }

    // feed Update
    @PutMapping("/ent/feed")
    public ResponseEntity<Boolean> updateFeed(@RequestBody EntFeedUpdateRequestDto requestDto){
        log.debug("[entFeed Update] EntFeedUpdateRequestDto : ", requestDto);
        return new ResponseEntity(feedService.updateFeed(requestDto), HttpStatus.OK);
    }

    // feed Delete
    @DeleteMapping("/ent/feed/{feedId}")
    public ResponseEntity<Boolean> deleteFeed(@PathVariable int feedId){
        log.debug("[entFeed Delete] feedId : ", feedId);
        return new ResponseEntity(feedService.delete(feedId),HttpStatus.OK);
    }




    // like ---------------------------------------------------------
    @PostMapping("/ent/feed/like")
    public ResponseEntity<String> like(@RequestBody EntFeedLikeRequestDto requestDto){
        log.debug("[entFeed] EntFeedLikeRequestDto : ",requestDto);

        return new ResponseEntity(feedService.like(requestDto),HttpStatus.OK);
    }





    // Comment -------------------------------------------------------
    // Comment Read
    @GetMapping("/ent/feed/comment/{feedId}")
    public ResponseEntity<EntFeedCommentResponseDto> readComment(@PathVariable int feedId){
        log.debug("[Feed Comment] feedId : ",feedId);
        return new ResponseEntity(feedService.readComment(feedId), HttpStatus.OK);
    }

    // Comment Create
    @PostMapping("/ent/feed/comment")
    public ResponseEntity<Boolean> createComment(@RequestBody EntFeedCommentRequestDto requestDto){
        log.debug("[Feed Comment Create] : ", requestDto);
        return new ResponseEntity(feedService.saveComment(requestDto),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/ent/feed/comment/{commentId}")
    public ResponseEntity<Boolean> deleteComment(@PathVariable int commentId){
        log.debug("[Comment Delete] commentId : ", commentId);
        return new ResponseEntity(feedService.deleteComment(commentId),HttpStatus.OK);
    }
}

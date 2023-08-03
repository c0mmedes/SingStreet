package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.entDto.*;
import com.ssafy.singstreet.ent.service.EntService;
import com.ssafy.singstreet.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor//final생성자 자동생성
@RestController
public class EntApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService userService;
    private final EntService entService;
    private final UserService userService;

    //Ent전체 목록 조회
    //page는 0부터 시작
    @GetMapping("/ent")
    public ResponseEntity<Slice<EntResponseDto>> read(@RequestParam int page, @RequestParam int size){
        log.debug("[readAll]");

        return new ResponseEntity(entService.read(page, size),HttpStatus.OK);
    }
    //Ent상세조회
    @GetMapping("/ent/{entId}")
    public ResponseEntity<EntResponseDto> readDetail(@PathVariable int entId){
        log.debug("[readDetail]entId = ", entId);

        return new ResponseEntity(entService.readDetail(entId),HttpStatus.OK);
    }
    //MyEnt 목록 조회
    @GetMapping("/ent/myEnt")
    public ResponseEntity<List<EntResponseDto>> readMyEnt(){
        int userId = userService.getCurrentUserId();

        log.debug("[readMyEntList] = ", userId);

        return new ResponseEntity(entService.readMyEnt(userId),HttpStatus.OK);
    }


    //Ent추가
    @PostMapping("/ent")
    public ResponseEntity<Boolean> create(@RequestBody EntSaveRequestDto requestDto){
        int userId = userService.getCurrentUserId();

        log.debug("[create]EntSaveRequestDto = ", requestDto);
        log.debug("[create]userId = ", userId);

        return new ResponseEntity(entService.create(requestDto, userId), HttpStatus.CREATED);
    }

    //Ent 수정
    @PutMapping("/ent/{entId}")
    public ResponseEntity<Boolean> update(@RequestBody EntSaveRequestDto requestDto, @PathVariable int entId){
        log.debug("[update]EntSaveRequestDto = ", requestDto);
        log.debug("[update]requestEntId = ", entId);

        return new ResponseEntity(entService.update(requestDto,entId),HttpStatus.OK);
    }

    //Ent삭제
    @PutMapping("/ent/delete/{entId}")
    public ResponseEntity<Boolean> delete(@PathVariable int entId){
        log.debug("[delete] entId = ",entId);

        return new ResponseEntity(entService.delete(entId),HttpStatus.OK);
    }

}

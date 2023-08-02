package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.entDto.EntDetailResponseDto;
import com.ssafy.singstreet.ent.model.entDto.EntPageListResponseDto;
import com.ssafy.singstreet.ent.model.entDto.EntListResponseDto;
import com.ssafy.singstreet.ent.model.entDto.EntSaveRequestDto;
import com.ssafy.singstreet.ent.service.EntService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.slf4j.LoggerFactory;

@RequiredArgsConstructor//final생성자 자동생성
@RestController
public class EntApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final EntService entService;

    //Ent전체 목록 조회
    //page는 0부터 시작
    @GetMapping("/ent")
    public EntPageListResponseDto read(@RequestParam int page,@RequestParam int size){
        log.debug("[readAll]");

        return entService.read(page, size);
    }
    //Ent상세조회
    @GetMapping("/ent/{entId}")
    public EntDetailResponseDto readDetail(@PathVariable int entId){
        log.debug("[readDetail]entId = ", entId);

        return entService.readDetail(entId);
    }
    //MyEnt 목록 조회
    @GetMapping("/ent/myEnt/{userId}")
    public EntListResponseDto readMyEnt(@PathVariable int userId){
        log.debug("[readMyEntList] = ", userId);

        return entService.readMyEnt(userId);
    }


    //Ent추가
    @PostMapping("/ent/{userId}")
    public ResponseEntity<Boolean> create(@RequestBody EntSaveRequestDto requestDto, @PathVariable int userId){
        log.debug("[create]EntSaveRequestDto = ", requestDto);
        log.debug("[create]userId = ", userId);

        return new ResponseEntity(entService.create(requestDto, userId), HttpStatus.CREATED);
    }

    //Ent 수정
    @PutMapping("/ent/{requestEntId}")
    public ResponseEntity<Boolean> update(@RequestBody EntSaveRequestDto requestDto, @PathVariable int requestEntId){
        log.debug("[update]EntSaveRequestDto = ", requestDto);
        log.debug("[update]requestEntId = ", requestEntId);

        return new ResponseEntity(entService.update(requestDto,requestEntId),HttpStatus.OK);
    }

    //Ent삭제
    @PutMapping("/ent/delete/{requestEntId}")
    public ResponseEntity<Boolean> delete(@PathVariable int requestEntId){
        log.debug("[delete]requestEntId = ",requestEntId);

        return new ResponseEntity(entService.delete(requestEntId),HttpStatus.OK);
    }

}

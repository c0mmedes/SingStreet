package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.EntDetailResponseDto;
import com.ssafy.singstreet.ent.model.EntPageListResponseDto;
import com.ssafy.singstreet.ent.model.EntResponseDto;
import com.ssafy.singstreet.ent.model.EntSaveRequestDto;
import com.ssafy.singstreet.ent.service.EntService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;


import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
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
    public EntResponseDto readMyEnt(@PathVariable int userId){
        log.debug("[readMyEntList] = ", userId);

        return entService.readMyEnt(userId);
    }


    //Ent추가
    @PostMapping("/ent/{userId}")
    public int create(@RequestBody EntSaveRequestDto requestDto, @PathVariable int userId){
        log.debug("[create]EntSaveRequestDto = ", requestDto);
        log.debug("[create]userId = ", userId);

        return entService.save(requestDto, userId);
    }

    //Ent 수정
    @PutMapping("/ent/{requestEntId}")
    public void update(@RequestBody EntSaveRequestDto requestDto, @PathVariable int requestEntId){
        log.debug("[update]EntSaveRequestDto = ", requestDto);
        log.debug("[update]requestEntId = ", requestEntId);
        entService.update(requestDto,requestEntId);
    }

    //Ent삭제
    @PutMapping("/ent/delete/{requestEntId}")
    public boolean delete(@PathVariable int requestEntId){
        log.debug("[delete]requestEntId = ",requestEntId);

        return entService.delete(requestEntId);
    }

}

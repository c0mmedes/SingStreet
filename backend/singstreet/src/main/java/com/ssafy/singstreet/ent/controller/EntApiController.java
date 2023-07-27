package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.EntSaveRequestDto;
import com.ssafy.singstreet.ent.service.EntService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;


import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@RestController
public class EntApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final EntService entService;

    //추가
    @PostMapping("/ent/{userId}")
    public int create(@RequestBody EntSaveRequestDto requestDto, @PathVariable int userId){
        log.debug("[create]EntSaveRequestDto = ", requestDto);
        log.debug("[create]userId = ", userId);

        return entService.save(requestDto, userId);
    }

    @PutMapping("/ent/{requestEntId}")
    public void update(@RequestBody EntSaveRequestDto requestDto, @PathVariable int requestEntId){
        log.debug("[update]EntSaveRequestDto = ", requestDto);
        log.debug("[update]requestEntId = ", requestEntId);
        entService.update(requestDto,requestEntId);
    }
}

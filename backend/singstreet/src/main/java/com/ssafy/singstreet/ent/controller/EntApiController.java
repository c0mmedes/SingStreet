package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.EntSaveRequestDto;
import com.ssafy.singstreet.ent.service.EntService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EntApiController {
    private final EntService entService;

    //추가
    @PostMapping("/ent")
    public void create(@RequestBody EntSaveRequestDto requestDto){
        entService.save(requestDto);
    }
}

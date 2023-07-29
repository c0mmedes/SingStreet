package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.db.entity.EntApplicant;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyRequestDto;
import com.ssafy.singstreet.ent.service.EntMemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class EntMemberApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final EntMemberService entMemberService;

    //EntApplicant 목록
    @GetMapping("ent/apply/{entId}")
    public List<EntApplicant> readAppl(@PathVariable int entId){
        return entMemberService.readAppl(entId);
    }


    //EntApplicant생성
    @PostMapping("/ent/apply")
    public boolean create(@RequestBody EntApplyRequestDto requestDto){
        log.debug("[create]EntApplicant = ",requestDto);

        return entMemberService.saveAppl(requestDto);
    }

    //EntMember생성
    @PostMapping("/ent/member/{applId}")
    public boolean create(@PathVariable int applId){
        log.debug("[create]EntMember = ",applId);

        return entMemberService.saveMember(applId);
    }




}

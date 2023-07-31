package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.db.entity.EntApplicant;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyRequestDto;
import com.ssafy.singstreet.ent.service.EntMemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor //final생성자 자동생성
@RestController
public class EntMemberApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final EntMemberService entMemberService;

    //지원자 -------------------------------------------------------------------
    //EntApplicant 목록
    @GetMapping("ent/apply/{entId}")
    public List<EntApplicant> readAppl(@PathVariable int entId){
        log.debug("[read]readAppl =", entId);
        return entMemberService.readAppl(entId);
    }


    //EntApplicant생성
    @PostMapping("/ent/apply")
    public boolean createAppl(@RequestBody EntApplyRequestDto requestDto){
        log.debug("[create]EntApplicant = ",requestDto);

        return entMemberService.saveAppl(requestDto);
    }

    //엔터 멤버 -------------------------------------------------------------------
    //EntMember 목록
    @GetMapping("/ent/member/{entId}")
    public List<EntMember> readMember(@PathVariable int entId){
        log.debug("[read]readMember =", entId);
        return entMemberService.readMember(entId);
    }

    //EntMember생성
    @PostMapping("/ent/member/{applId}")
    public boolean createMember(@PathVariable int applId){
        log.debug("[create]EntMember = ",applId);

        return entMemberService.saveMember(applId);
    }

    //Ent 탈퇴
    @PutMapping("/ent/leave/{memberId}")
    public boolean deleteMember(@PathVariable int memberId){
        log.debug("[delete]EntMember =", memberId );
        return entMemberService.deleteMember(memberId);
    }

}

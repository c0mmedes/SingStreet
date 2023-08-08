package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.db.entity.EntApplicant;
import com.ssafy.singstreet.ent.db.entity.EntMember;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyDetailResponseDto;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyRequestDto;
import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyResponseDto;
import com.ssafy.singstreet.ent.model.entMemberDto.EntMemberResponseDto;
import com.ssafy.singstreet.ent.service.EntMemberService;
import com.ssafy.singstreet.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor //final생성자 자동생성
@RestController
public class EntMemberApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final EntMemberService entMemberService;
    private final UserService userService;

    //지원자 -------------------------------------------------------------------
    // 지원자(EntApplicant) 목록
    @GetMapping("ent/apply/{entId}")
    public ResponseEntity<List<EntApplyResponseDto>> readAppl(@PathVariable int entId){
        log.debug("[read]readAppl =", entId);
        return new ResponseEntity(entMemberService.readAppl(entId), HttpStatus.OK);
    }

    // 지원자(EntApplicant) 상세
    @GetMapping("ent/apply/detail/{applId}")
    public ResponseEntity<EntApplyDetailResponseDto> readDetail(@PathVariable int applId){
        log.debug("[Read ApplicantDetail] applId :", applId);

        return new ResponseEntity(entMemberService.readApplDetail(applId),HttpStatus.OK);
    }


    // 지원자(EntApplicant) 생성
    @PostMapping("/ent/apply")
    public ResponseEntity<Boolean> createAppl(@RequestBody EntApplyRequestDto requestDto){
        int userId = userService.getCurrentUserId();
        requestDto.updateUserId(userId);
        log.debug("[create]EntApplicant = ",requestDto);

        return new ResponseEntity(entMemberService.saveAppl(requestDto),HttpStatus.CREATED);

    }



    //엔터 멤버 -------------------------------------------------------------------
    // EntMember 목록
    @GetMapping("/ent/member/{entId}")
    public ResponseEntity<List<EntMemberResponseDto>> readMember(@PathVariable int entId){
        log.debug("[read]readMember =", entId);

        return new ResponseEntity(entMemberService.readMember(entId),HttpStatus.OK);
    }


    // EntMember 결정
    @PostMapping("/ent/member/{applId}/{isAccepted}")
    public ResponseEntity<Boolean> accepte(@PathVariable int applId, @PathVariable boolean isAccepted){
        log.debug("[create EntMember] applId = ",applId);
        log.debug("[create EntMember] isAccepted = ",isAccepted);

        return new ResponseEntity(entMemberService.accepteMember(applId,isAccepted),HttpStatus.CREATED);
    }

    // Ent 탈퇴
    @PutMapping("/ent/leave/{memberId}")
    public ResponseEntity<Boolean> deleteMember(@PathVariable int memberId){
        log.debug("[delete]EntMember =", memberId );

        return new ResponseEntity(entMemberService.deleteMember(memberId),HttpStatus.OK);
    }

}

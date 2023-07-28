package com.ssafy.singstreet.ent.controller;

import com.ssafy.singstreet.ent.model.entMemberDto.EntApplyRequestDto;
import com.ssafy.singstreet.ent.service.EntMemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@RestController
public class EntMemberApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final EntMemberService entMemberService;

    //EntApplicant생성
    @PostMapping("/ent/apply")
    public boolean create(@RequestBody EntApplyRequestDto requestDto){
        log.debug("[create]EntApplicant = ",requestDto);

        return entMemberService.save(requestDto);
    }

}

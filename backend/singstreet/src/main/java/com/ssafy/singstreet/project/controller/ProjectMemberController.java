package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectInvited;
import com.ssafy.singstreet.project.model.*;
import com.ssafy.singstreet.project.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/member")
@CrossOrigin("*")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    // 프로젝트 떠나기
    @PutMapping("/{projecId}/{userId}")
    public ResponseEntity<String> projectLeaveMember(@PathVariable Integer projecId, @PathVariable Integer userId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            projectMemberService.projectLeaveMember(projecId, userId);
            return ResponseEntity.ok("프로젝트 탈퇴가 처리되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("처리 중 오류가 발생했습니다.");
        }
    }

    // 프로젝트 초대
    @PutMapping
    public ResponseEntity<ProjectInvited> projectInviteMember(@RequestBody ProjectInvitedRequestDto dto) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ProjectInvited projectInvited = projectMemberService.projectInviteMember(dto);

        // 이미 프로젝트 멤버인 경우, 실패 상태와 함께 null 값을 반환
        if (projectInvited == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>(projectInvited, HttpStatus.CREATED);
    }

    // 프로젝트 참여
    @PutMapping("/joinProject")
    public ResponseEntity<String> joinProject(@RequestBody ProjectJoinDto dto) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            projectMemberService.acceptProjectInvite(dto);
            return ResponseEntity.ok("프로젝트 멤버 수락/거부가 처리되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("처리 중 오류가 발생했습니다.");
        }
    }

    // 프로젝트 멤버 조회
    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectMemberDto>> getProjectMembers(@PathVariable Integer projectId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<ProjectMemberDto> ProjectMembers = projectMemberService.getProjectMembers(projectId);
        if (ProjectMembers == null || ProjectMembers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ProjectMembers, HttpStatus.OK);
    }

    // 프로젝트 초대자 목록 조회
    @GetMapping("invited/{projectId}")
    public ResponseEntity<List<ProjectInvitedMemberDto>> getProjectInvitedMembers(@PathVariable Integer projectId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<ProjectInvitedMemberDto> invitedMembers = projectMemberService.getProjectInvitedMember(projectId);
        if (invitedMembers == null || invitedMembers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(invitedMembers, HttpStatus.OK);
    }

//    // 내가 속한 프로젝트 목록
//    @GetMapping("myProject/{userId}")
//    public ResponseEntity<List<ProjectSaveResponseDto>> getProjectInvitedMembers(@PathVariable Integer userId) {
//        // 토큰 검증 및 인증 실패 처리
//        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//
//        List<ProjectInvitedMemberDto> invitedMembers = projectMemberService.getMyProject(projectId);
//        if (invitedMembers == null || invitedMembers.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(invitedMembers, HttpStatus.OK);
//    }
}

package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectInvited;
import com.ssafy.singstreet.project.model.ProjectInvitedRequestDto;
import com.ssafy.singstreet.project.model.ProjectMemberDto;
import com.ssafy.singstreet.project.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/member")
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @GetMapping("/{project_id}")
    public ResponseEntity<List<ProjectMemberDto>> getProjectMembers(@PathVariable Integer project_id) {
        List<ProjectMemberDto> ProjectMemberDtos = projectMemberService.getProjectMembers(project_id);
        if (ProjectMemberDtos == null || ProjectMemberDtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ProjectMemberDtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProjectInvited> projectInviteMember(@RequestBody ProjectInvitedRequestDto dto) {
        ProjectInvited projectInvited = projectMemberService.projectInviteMember(dto);
        // 프로젝트가 성공적으로 생성되었을 때 201 Created 상태코드와 생성된 프로젝트를 응답합니다.
        return new ResponseEntity<>(projectInvited, HttpStatus.CREATED);
    }

    @PutMapping("/joinProject")
    public ResponseEntity<String> joinProject(@RequestBody ProjectInvitedRequestDto dto) {
        try {
            projectMemberService.acceptProjectInvite(dto);
            return ResponseEntity.ok("프로젝트 멤버 수락/거부가 처리되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("처리 중 오류가 발생했습니다.");
        }
    }
}

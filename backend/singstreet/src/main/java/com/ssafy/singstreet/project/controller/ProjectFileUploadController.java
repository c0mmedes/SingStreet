package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.service.ProjectFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/upload")
public class ProjectFileUploadController {

    private final ProjectFileUploadService projectFileUploadService;

    // 음원명 업로드
    @PutMapping("/audio/{projectId}/{audioFileName}")
    public ResponseEntity<Project> updateAudioFile(@PathVariable Integer projectId, @PathVariable String audioFileName) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = projectFileUploadService.updateAudioFile(projectId, audioFileName);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    // 영상이름 업로드
    @PutMapping("/file/{projectId}/{originFileName}")
    public ResponseEntity<Project> updateOriginFilename(@PathVariable Integer projectId, @PathVariable String originFileName) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = projectFileUploadService.updateOriginFilename(projectId, originFileName);

        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

}

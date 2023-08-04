package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectLikes;
import com.ssafy.singstreet.project.model.ProjectLikeRequestDto;
import com.ssafy.singstreet.project.model.ProjectLikeResponseDto;
import com.ssafy.singstreet.project.service.ProjectFileUploadService;
import com.ssafy.singstreet.project.service.ProjectLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/like")
public class ProjectLikeController {
    private final ProjectLikeService projectLikeService;

    // 좋아요 추가
    @PostMapping
    public ResponseEntity<ProjectLikes> addLikeProject(@RequestBody ProjectLikeRequestDto dto) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ProjectLikes projectLikes = projectLikeService.addLikeProject(dto);
        return new ResponseEntity<>(projectLikes, HttpStatus.CREATED);
    }

    // 좋아요 삭제
    @DeleteMapping("{userId}/{projectId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer userId, @PathVariable Integer projectId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean deletionSuccess = projectLikeService.deleteLikeProject(userId, projectId);

        if (deletionSuccess) {
            // 댓글 삭제 성공 시 200 OK 상태코드와 성공 메시지를 반환합니다.
            return new ResponseEntity<>("Like deleted successfully", HttpStatus.OK);
        } else {
            // 댓글 삭제 실패 시 404 NOT FOUND 상태코드와 실패 메시지를 반환합니다.
            return new ResponseEntity<>("Like not found or deletion failed", HttpStatus.NOT_FOUND);
        }
    }

    // 내가 좋아요한 프로젝트 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<ProjectLikeResponseDto>> getMyLikedProjects(@PathVariable Integer userId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<ProjectLikeResponseDto> likedProjects = projectLikeService.getMyLikedProjects(userId);

        if (likedProjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(likedProjects, HttpStatus.OK);
    }
}


package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.db.entity.ProjectComment;
import com.ssafy.singstreet.project.model.ProjectCommentRequestDto;
import com.ssafy.singstreet.project.model.ProjectCommentResponseDto;
import com.ssafy.singstreet.project.service.ProjectCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/comment")
@RequiredArgsConstructor
public class ProjectCommentController {

    private final ProjectCommentService projectCommentService;

    // 댓글 등록
    @PostMapping
    public ResponseEntity<ProjectComment> createComment(@RequestBody ProjectCommentRequestDto dto) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ProjectComment projectComment = projectCommentService.createComment(dto);
        return new ResponseEntity<>(projectComment, HttpStatus.CREATED);
    }

    // 댓글 삭제
    @DeleteMapping("{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer commentId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        boolean deletionSuccess = projectCommentService.deleteComment(commentId);

        if (deletionSuccess) {
            // 댓글 삭제 성공 시 200 OK 상태코드와 성공 메시지를 반환합니다.
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } else {
            // 댓글 삭제 실패 시 404 NOT FOUND 상태코드와 실패 메시지를 반환합니다.
            return new ResponseEntity<>("Comment not found or deletion failed", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectCommentResponseDto>> getProjectComments(
            @PathVariable Integer projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<ProjectCommentResponseDto> comments = projectCommentService.getProjectComments(projectId, pageable);

        // 댓글 목록이 비어있을 경우 NOT_FOUND 상태코드 반환
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}

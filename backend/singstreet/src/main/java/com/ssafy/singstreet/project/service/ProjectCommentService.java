package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectComment;
import com.ssafy.singstreet.project.db.repo.ProjectCommentRepository;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.model.ProjectCommentRequestDto;
import com.ssafy.singstreet.project.model.ProjectCommentResponseDto;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectCommentService {

    private final ProjectCommentRepository projectCommentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    // 댓글 쓰기
    public ProjectComment createComment(ProjectCommentRequestDto dto) {
        User user = userRepository.findByUserId(dto.getUserId());
        Project project = projectRepository.findByProjectId(dto.getProjectId());

        ProjectComment projectComment = ProjectComment.builder()
                .project(project)
                .user(user)
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .build();

        projectCommentRepository.save(projectComment);

        return projectComment;
    }

    // 댓글 삭제
    public Boolean deleteComment(Integer commentId) {
        ProjectComment projectComment = projectCommentRepository.findById(commentId).orElse(null);

        if (projectComment != null) {
            projectCommentRepository.delete(projectComment);
            return true;
        } else {
            return false;
        }
    }

    // 댓글 목록 조회 (페이징)
    public List<ProjectCommentResponseDto> getProjectComments(Integer projectId, Pageable pageable) {
        Project project = projectRepository.findByProjectId(projectId);
        if (project == null) {
            // 프로젝트가 존재하지 않을 경우 빈 목록 반환 또는 예외 처리
            return Collections.emptyList();
        }

        Page<ProjectComment> commentsPage = projectCommentRepository.findByProjectOrderByCreatedAtDesc(project, pageable);
        List<ProjectComment> comments = commentsPage.getContent();

        return comments.stream()
                .map(comment -> ProjectCommentResponseDto.builder()
                        .nickname(comment.getUser().getNickname())
                        .content(comment.getContent())
                        // 다른 필드들도 필요한대로 추가
                        .build())
                .collect(Collectors.toList());
    }
}

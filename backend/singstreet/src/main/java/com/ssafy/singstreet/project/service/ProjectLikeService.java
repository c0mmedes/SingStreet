package com.ssafy.singstreet.project.service;


import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectLikes;
import com.ssafy.singstreet.project.db.entity.ProjectLikesId;
import com.ssafy.singstreet.project.db.repo.ProjectInvitedRepository;
import com.ssafy.singstreet.project.db.repo.ProjectLikeRepository;
import com.ssafy.singstreet.project.db.repo.ProjectMemberRepository;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.model.ProjectLikeRequestDto;
import com.ssafy.singstreet.project.model.ProjectLikeResponseDto;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectLikeService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectInvitedRepository projectInvitedRepository;
    private final EntRepository entRepository;
    private final ProjectLikeRepository projectLikeRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    // 좋아요 추가
    public ProjectLikes addLikeProject(ProjectLikeRequestDto dto) {
        Project project = projectRepository.findByProjectId(dto.getProjectId());
        User user = userRepository.findByUserId(dto.getUserId());

        // 이미 해당 프로젝트와 사용자 아이디로 좋아요가 있는지 확인
        ProjectLikes existingLike = projectLikeRepository.findById_ProjectAndId_User(project, user);

        if (existingLike != null) {
            // 이미 좋아요가 있는 경우 처리
            return null;
        }

        ProjectLikesId projectLikesId = ProjectLikesId.builder()
                .project(project)
                .user(user)
                .build();

        ProjectLikes projectLikes = ProjectLikes.builder()
                .id(projectLikesId)
                .build();

        projectLikeRepository.save(projectLikes);

        // 좋아요 카운트 증가
        project.plusLikeCount();
        projectRepository.save(project);

        return projectLikes;
    }

    // 좋아요 삭제
    @Transactional
    public boolean deleteLikeProject(Integer userId, Integer projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        User user = userRepository.findByUserId(userId);

        if (project == null || user == null) {
            return false;
        } else {
            projectLikeRepository.deleteById_User_UserIdAndId_Project_ProjectId(user.getUserId(), project.getProjectId());

            // 좋아요 카운트 감소
            project.minusLikeCount();
            projectRepository.save(project);

            return true;
        }
    }

    // 마이페이지 좋아요 조회
    public List<ProjectLikeResponseDto> getMyLikedProjects(Integer userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return Collections.emptyList(); // 혹은 빈 목록 등을 반환
        }

        List<ProjectLikeResponseDto> likedProjects = projectLikeRepository.findById_User(user)
                .stream()
                .map(like -> ProjectLikeResponseDto.builder()
                        .projectId(like.getId().getProject().getProjectId())
                        .entName(like.getId().getProject().getEnt().getEntName())
                        .singerName(like.getId().getProject().getSingerName())
                        .singName(like.getId().getProject().getSingName())
                        .build())
                .collect(Collectors.toList());

        return likedProjects;
    }

}
package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectInvited;
import com.ssafy.singstreet.project.db.entity.ProjectMember;
import com.ssafy.singstreet.project.db.repo.ProjectInvitedRepository;
import com.ssafy.singstreet.project.db.repo.ProjectMemberRepository;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.model.ProjectInvitedRequestDto;
import com.ssafy.singstreet.project.model.ProjectMemberDto;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectInvitedRepository projectInvitedRepository;
    private final EntRepository entRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    // 프로젝트 멤버 목록 조회
    public List<ProjectMemberDto> getProjectMembers(Integer projectId) {
        List<ProjectMember> projectMembers = projectMemberRepository.findByProjectMemberIdProjectProjectId(projectId);

        // ProjectMember 엔티티를 ProjectMemberDto 변환하여 리스트에 담기
        List<ProjectMemberDto> ProjectMemberDtos = new ArrayList<>();
        for (ProjectMember projectmember : projectMembers) {
            Integer userId = null;
            if (projectmember.getProjectMemberId() != null && projectmember.getProjectMemberId().getUser().getUserId() != null) {
                userId = projectmember.getProjectMemberId().getUser().getUserId();
            }
            ProjectMemberDto responseDTO = ProjectMemberDto.builder()
                    .userId(userId)
                    .isLeader(projectmember.getIsLeader())
                    .isDeleted(projectmember.getIsDeleted())
                    .build();
            ProjectMemberDtos.add(responseDTO);
        }

        return ProjectMemberDtos;
    }

    // 프로젝트 멤버 초대
    public ProjectInvited projectInviteMember(ProjectInvitedRequestDto dto) {
        Ent entId = entRepository.findById(dto.getEntId()).orElse(null);
        User userId = userRepository.findById(dto.getUserId()).orElse(null);
        Project projectId = projectRepository.findById(dto.getProjectId()).orElse(null);

        if (entId == null || userId == null || projectId == null) {
            // entId 또는 userId에 해당하는 Ent 또는 User가 존재하지 않는 경우 처리
            throw new IllegalArgumentException("Invalid entId or userId.");
        }

        ProjectInvited projectInvited = ProjectInvited.builder()
                .ent(entId)
                .user(userId)
                .project(projectId)
                .createdAt(LocalDateTime.now())
                .build();

        // DB에 넣기
        projectInvitedRepository.save(projectInvited);
        return projectInvited;
    }

    public void acceptProjectInvite(ProjectInvitedRequestDto dto) {
        Boolean isAccepted = dto.getIsAccept(); // 수락여부

//        Ent entId = entRepository.findById(dto.getEntId()).orElse(null);
        User userId = userRepository.findById(dto.getUserId()).orElse(null);
        Project projectId = projectRepository.findById(dto.getProjectId()).orElse(null);

        ProjectInvited projectInvited = projectInvitedRepository.findById(dto.getProjectId()).orElseThrow(() -> new IllegalArgumentException("Invalid projectId."));

        // 확인일시 갱신
        projectInvited.setConfirmDate(LocalDateTime.now());

        if (isAccepted) {
            // 초대 수락 -> 프로젝트 초대자 테이블 (승인여부, true)
            projectInvited.setIsAccepted(true);

            // 프로젝트 멤버 테이블에 멤버 추가
            ProjectMember projectMember = new ProjectMember();
            projectMember.getProjectMemberId().setUser(userId);
            projectMember.getProjectMemberId().setProject(projectId);

            projectMemberRepository.save(projectMember);
        } else {
            // 초대 거부 -> 프로젝트 초대자 테이블 (승인여부, false)
            projectInvited.setIsAccepted(false);
        }

        // 변경 사항을 저장
        projectInvitedRepository.save(projectInvited);
    }
}

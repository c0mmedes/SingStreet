package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectInvited;
import com.ssafy.singstreet.project.db.entity.ProjectMember;
import com.ssafy.singstreet.project.db.entity.ProjectMemberId;
import com.ssafy.singstreet.project.db.repo.ProjectInvitedRepository;
import com.ssafy.singstreet.project.db.repo.ProjectMemberRepository;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.model.ProjectInvitedMemberDto;
import com.ssafy.singstreet.project.model.ProjectInvitedRequestDto;
import com.ssafy.singstreet.project.model.ProjectJoinDto;
import com.ssafy.singstreet.project.model.ProjectMemberDto;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectInvitedRepository projectInvitedRepository;
    private final EntRepository entRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    // 프로젝트 탈퇴 여부 처리
    public ProjectMember projectLeaveMember(Integer projectId, Integer userId) {
        // User와 Project 객체를 찾습니다.
        User user = userRepository.findById(userId).orElse(null);
        Project project = projectRepository.findById(projectId).orElse(null);
        System.out.println("user" + user.getUserId());
        System.out.println("project" + project.getProjectId());

        if (user == null || project == null) {
            System.out.println("여기가문제야? ");
            // 유저나 프로젝트를 찾지 못하면 null을 반환하거나 예외 처리를 해줍니다.
            return null;
        }
        ProjectMember projectMember = projectMemberRepository.findByProjectIdAndUserId(projectId, user.getUserId());

//      projectMember가 null이 아니라면 멤버를 삭제합니다.
        if (projectMember != null) {
            projectMember.leave();
            // 데이터베이스에 반영합니다.
            projectMemberRepository.save(projectMember);
        }
        return projectMember;
    }

    // 프로젝트 멤버 초대
    public ProjectInvited projectInviteMember(ProjectInvitedRequestDto dto) {
        Ent entId = entRepository.findById(dto.getEntId()).orElse(null);
        User userId = userRepository.findById(dto.getUserId()).orElse(null);
        Project projectId = projectRepository.findById(dto.getProjectId()).orElse(null);

        Boolean isAccepted = projectInvitedRepository.existsByUserUserIdAndIsAcceptedIsTrue(userId.getUserId());
        // isAccepted가 true가 아닐 때만 초대 가능
        if(isAccepted == true) return null;


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

    // 프로젝트 초대 수락
    public void acceptProjectInvite(ProjectJoinDto dto) {
        Boolean isAccepted = dto.getIsAccept(); // 수락여부

        User userId = userRepository.findById(dto.getUserId()).orElse(null);
        Project projectId = projectRepository.findById(dto.getProjectId()).orElse(null);

        ProjectInvited projectInvited = (ProjectInvited) projectInvitedRepository.findByProjectProjectId(dto.getProjectId()).orElse(null);

        // 확인일시 갱신
        projectInvited.setConfirmDate(LocalDateTime.now());

        if (isAccepted) {
            // 초대 수락 -> 프로젝트 초대자 테이블 (승인여부, true)
            projectInvited.accept();

            // 프로젝트 멤버 테이블에 멤버 추가
            ProjectMember projectMember = ProjectMember.builder()
                    .projectMemberId(
                            ProjectMemberId.builder()
                                    .user(userId)
                                    .project(projectId)
                                    .build()
                    )
                    .createdAt(LocalDateTime.now())
                    .build();
            projectMemberRepository.save(projectMember);
        } else {
            // 초대 거부 -> 프로젝트 초대자 테이블 (승인여부, false)
            projectInvited.reject();
        }

        // 변경 사항을 저장
        projectInvitedRepository.save(projectInvited);
    }

    // 프로젝트 멤버 목록 조회
    public List<ProjectMemberDto> getProjectMembers(Integer projectId) {
        List<ProjectMember> projectMembers = projectMemberRepository.findByProjectMemberIdProjectProjectIdAndIsDeletedIsFalse(projectId);

        // ProjectMember 엔티티를 ProjectMemberDto 변환하여 리스트에 담기
        List<ProjectMemberDto> ProjectMemberDtos = new ArrayList<>();
        for (ProjectMember projectmember : projectMembers) {
            Integer userId = null;
            if (projectmember.getProjectMemberId() != null && projectmember.getProjectMemberId().getUser().getUserId() != null) {
                userId = projectmember.getProjectMemberId().getUser().getUserId();
            }
            ProjectMemberDto responseDTO = ProjectMemberDto.builder()
                    .userId(userId)
                    .projectId(projectId)
                    .isLeader(projectmember.getIsLeader())
                    .isDeleted(projectmember.getIsDeleted())
                    .createdAt(projectmember.getCreatedAt())
                    .build();
            ProjectMemberDtos.add(responseDTO);
        }

        return ProjectMemberDtos;
    }

    // 프로젝트 초대자 목록보기
    public List<ProjectInvitedMemberDto> getProjectInvitedMember(Integer projectId) {
        List<ProjectInvited> projectInviteds = projectInvitedRepository.findByProjectId(projectId);

        List<ProjectInvitedMemberDto> dtos = projectInviteds.stream()
                .map(invited -> ProjectInvitedMemberDto.builder()
                        .userId(invited.getUser().getUserId())
                        .nickname(invited.getUser().getNickname())
                        .createdAt(invited.getCreatedAt())
                        // 다른 필드들도 필요한대로 추가
                        .build())
                .collect(Collectors.toList());

        return dtos;
    }

}

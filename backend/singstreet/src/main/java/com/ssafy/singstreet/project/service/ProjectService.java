package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.model.ProjectSaveRequestDto;
import com.ssafy.singstreet.project.model.ProjectSaveResponseDto;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EntRepository entRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, EntRepository entRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.entRepository = entRepository;
        this.userRepository = userRepository;
    }

    // 프로젝트 생성
    public Project createProject(ProjectSaveRequestDto dto) {
        // Ent와 User 엔티티를 가져옴
        Ent ent = entRepository.findById(dto.getEntId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        if (ent == null || user == null) {
            // entId 또는 userId에 해당하는 Ent 또는 User가 존재하지 않는 경우 처리
            // 예를 들어, 예외를 던지거나 기본값을 설정하는 등의 방법을 사용할 수 있습니다.
            throw new IllegalArgumentException("Invalid entId or userId.");
        }

        // ProjectSaveRequestDto를 Project 엔티티로 변환하고, Ent와 User를 설정
        Project project = Project.builder()
                .projectId(dto.getProjectId())
                .ent(ent)
                .user(user)
                .projectName(dto.getProjectName())
                .singerName(dto.getSingerName())
                .singName(dto.getSingName())
                .projectInfo(dto.getProjectInfo())
                .projectImg(dto.getProjectImg())
                // 필요한 다른 필드들도 추가
                .build();

        // Project 엔티티를 DB에 저장
        projectRepository.save(project);
        return project;
    }

    // 프로젝트 삭제 여부 처리
    public Project deleteProject(Integer projectId, ProjectSaveRequestDto dto) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project == null) {
            throw new IllegalArgumentException("Invalid projectId.");
        }

        // Project 엔티티의 필드들을 dto로 업데이트
        project = Project.builder()
                .isCompleted(true)
                .completedAt(LocalDateTime.now())
                .build();

        // 변경 감지에 의해 자동으로 DB에 업데이트 됨 (명시적인 save() 호출이 필요 없음)
        return project;
    }

    // 프로젝트 수정
    public Project updateProject(Integer projectId, ProjectSaveRequestDto dto) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project == null) {
            throw new IllegalArgumentException("Invalid projectId.");
        }

        // 기존 엔티티의 필드들을 새로운 DTO 값으로 업데이트하는 방법 (Builder 패턴 사용)
        Project updatedProject = Project.builder()
                .projectId(project.getProjectId())
                .ent(project.getEnt())
                .user(project.getUser())
                .projectName(dto.getProjectName())
                .singerName(dto.getSingerName())
                .singName(dto.getSingName())
                .projectInfo(dto.getProjectInfo())
                .projectImg(dto.getProjectImg())
                .likeCount(project.getLikeCount())
                .hitCount(project.getHitCount())
                .monthlyLikeCount(project.getMonthlyLikeCount())
                .isCompleted(project.isCompleted())
                .isDestroyed(project.isDestroyed())
                .originFilename(project.getOriginFilename())
                .lastEnterDate(project.getLastEnterDate())
                .build();

        // 변경 감지에 의해 자동으로 DB에 업데이트 됨
        return projectRepository.save(updatedProject);
    }


    // 프로젝트 상세조회
    public Project getProjectById(Integer projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    // 프로젝트 전체 조회
    public List<ProjectSaveResponseDto> getAllProjects() {
        List<Project> projects = projectRepository.findAll();

        // Project 엔티티를 ProjectResponseDTO로 변환하여 리스트에 담기
        List<ProjectSaveResponseDto> projectResponseDTOs = new ArrayList<>();
        for (Project project : projects) {
            ProjectSaveResponseDto responseDTO = ProjectSaveResponseDto.builder()
                    .projectId(project.getProjectId())
                    .entId(project.getEnt().getEntId())
                    .projectName(project.getProjectName())
                    .singerName(project.getSingerName())
                    .singName(project.getSingName())
                    .projectInfo(project.getProjectInfo())
                    .projectImg(project.getProjectImg())
                    .build();
            projectResponseDTOs.add(responseDTO);
        }

        return projectResponseDTOs;
    }
}

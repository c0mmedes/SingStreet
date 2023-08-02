package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.project.db.entity.*;
import com.ssafy.singstreet.project.db.repo.*;
import com.ssafy.singstreet.project.model.ProjectInvitedResponseDto;
import com.ssafy.singstreet.project.model.ProjectSaveRequestDto;
import com.ssafy.singstreet.project.model.ProjectSaveResponseDto;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {


    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectInvitedRepository projectInvitedRepository;
    private final EntRepository entRepository;
    private final UserRepository userRepository;
    private final ProjectTagRepository tagRepository;
    private final PartRepository partRepository;


    // 가져온 태그리스트를 태그테이블에 넣어주기
    public void saveTagList(String[] tagList, Project projectId){ // tag 생성
        for (String tag : tagList) {
            tagRepository.save(ProjectTag
                    .builder()
                    .projectId(projectId)
                    .tagName(tag)
                    .build());
        }
    }

    // 프로젝트 생성
    public Project createProject(ProjectSaveRequestDto dto) {
        // Ent와 User 엔티티를 가져옴
        Ent ent = entRepository.findById(dto.getEntId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        // ProjectSaveRequestDto를 Project 엔티티로 변환하고, Ent와 User를 설정
        Project project = Project.builder()
                .ent(ent)
                .user(user)
                .projectName(dto.getProjectName())
                .singerName(dto.getSingerName())
                .singName(dto.getSingName())
                .projectInfo(dto.getProjectInfo())
                .projectImg(dto.getProjectImg())
                .isVisible(dto.getIsVisible())
                .build();

        // Project 엔티티를 DB에 저장
        projectRepository.save(project);

        // 프로젝트 멤버 생성
        ProjectMemberId projectMemberId = new ProjectMemberId(project, user);
        ProjectMember projectMember = ProjectMember.builder()
                .projectMemberId(projectMemberId)
                .isLeader(true) // 리더로 지정 (원하는 조건으로 변경 가능)
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .build();
        projectMemberRepository.save(projectMember);

        System.out.println(dto);
        Project projectId = projectRepository.findByProjectId(project.getProjectId());
        // tagList에 #을 기준으로 짤라서 저장
        String[] tagList = dto.getProjectTagList().split("\\s*#\\s*");
        saveTagList(tagList, projectId);

        for(String partName : dto.getPartList()){
            Part part = Part.builder()
                    .project(projectRepository.findByProjectId(projectId.getProjectId()))
                    .partName(partName)
                    .build();
            partRepository.save(part);
        }

        if (ent == null || user == null) {
            // entId 또는 userId에 해당하는 Ent 또는 User가 존재하지 않는 경우 처리
            throw new IllegalArgumentException("Invalid entId or userId.");
        }
        return project;
    }

    // 프로젝트 수정
    public Project updateProject(Integer projectId, ProjectSaveRequestDto dto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Invalid projectId."));

        // Project 엔티티의 update 메서드 호출하여 필드 업데이트
        project.update(
                dto.getProjectName(),
                dto.getSingerName(),
                dto.getSingName(),
                dto.getProjectInfo(),
                dto.getProjectImg(),
                dto.getIsRecruited(),
                dto.getIsVisible()
                // 나머지 필드들도 필요에 따라 추가
        );

        // 프로젝트 태그들 업데이트
        if (dto.getProjectTagList() != null) {
            // 기존 태그들 삭제
            List<ProjectTag> currentTagList = tagRepository.findAllByProjectId(project);
            tagRepository.deleteAll(currentTagList);

            // 새로운 태그들 추가
            String[] newTagList = dto.getProjectTagList().split("\\s*#\\s*");
            saveTagList(newTagList, project);
        }

        // 파트 수정
        // 파트 수정하는 부분은 따로 짜야되나 ?
        // 여러 명의 userid를 어떻게넣어줄까 (user dto list를 만들어서 받아와야됨?)
        /*{
            "entId" : 1,
                "userId" : 1,
                "projectName": "수정된 프로젝트 이름",
                "singerName": "수정된 가수 이름",
                "singName": "수정된 노래 제목",
                "projectInfo": "수정된 프로젝트 정보",
                "projectImg": "https://example.com/updated_image.jpg",
                "isRecruited": true,
                "projectTagList": "#태그1 #태그2 #태그3",
                "partList": ["파트1", "파트2", "파트3"]
        }*/
        for(String partName : dto.getPartList()){
            User user = userRepository.findByUserId(dto.getUserId());
            Part part = Part.builder()
                    .project(projectRepository.findByProjectId(projectId))
                    .partName(partName)
                    .user(user)
                    .build();
            partRepository.save(part);
        }

        // 변경 감지에 의해 자동으로 DB에 업데이트 됨
        return projectRepository.save(project);
    }

    // 프로젝트 삭제 여부 처리
    public Project deleteProject(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) project.delete();
        projectRepository.save(project);
        return project;
    }

    // 프로젝트 상세조회
    public Project getProjectById(Integer projectId) {
        return projectRepository.findById(projectId).orElse(null);
    }

    // 페이징 전체 조회
    public Page<ProjectSaveResponseDto> pageList(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findByIsDestroyedFalse(pageable);
        return projectPage.map(this::convertToDto);
    }

    private ProjectSaveResponseDto convertToDto(Project project) {
        return ProjectSaveResponseDto.builder()
                .projectId(project.getProjectId())
                .entId(project.getEnt().getEntId())
                .userId(project.getUser().getUserId())
                .projectName(project.getProjectName())
                .singerName(project.getSingerName())
                .singName(project.getSingName())
                .projectInfo(project.getProjectInfo())
                .projectImg(project.getProjectImg())
                .likeCount(project.getLikeCount())
                .hitCount(project.getHitCount())
                .monthlyLikeCount(project.getMonthlyLikeCount())
                .isCompleted(project.isCompleted())
                .isDestroyed(project.isDestroyed())
                .originFilename(project.getOriginFilename())
                .lastEnterDate(project.getLastEnterDate())
                .build();
    }

    // 프로젝트 키워드 검색
    public Page<ProjectSaveResponseDto> getProjectByKeyword(String keyword, Pageable pageable) {
        Page<Project> projectsPage = projectRepository.findByIsDestroyedFalseAndKeyword(keyword, pageable);

        List<ProjectSaveResponseDto> projectResponseDTOs = new ArrayList<>();
        for (Project project : projectsPage.getContent()) {
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
        return new PageImpl<>(projectResponseDTOs, pageable, projectsPage.getTotalElements());
    }

    // 프로젝트 태그 검색
    public Page<ProjectSaveResponseDto> getProjectByTag(String tag, Pageable pageable) {
        // TagRepository를 사용하여 tag에 해당하는 ProjectTag 리스트를 가져옴
        List<ProjectTag> projectTags = tagRepository.findByTagName(tag);

        // ProjectTag에서 Project의 리스트를 추출
        List<Project> projects = projectTags.stream()
                .map(ProjectTag::getProjectId) // getProject()를 사용하여 Project 엔티티로 변환
                .filter(project -> !project.isDestroyed()) // isDestroyed가 false인 프로젝트만 필터링
                .collect(Collectors.toList());

        // Project 엔티티를 ProjectSaveResponseDto로 변환하여 반환
        List<ProjectSaveResponseDto> projectResponseDtoList = projects.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        // 총 항목 수 계산
        int totalElements = projectResponseDtoList.size();

        // 페이징 처리된 리스트 생성
        int fromIndex = (int) pageable.getOffset();
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), totalElements);
        List<ProjectSaveResponseDto> pagedProjectResponseDtoList = projectResponseDtoList.subList(fromIndex, toIndex);

        // PageImpl 생성
        return new PageImpl<>(pagedProjectResponseDtoList, pageable, totalElements);
    }

    // 내 프로젝트 조회
    public List<ProjectSaveResponseDto> getMyProject(int userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
                throw new IllegalArgumentException("Invalid userId.");
        }

        List<Project> projects = projectRepository.findByUser(user);
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

    public List<ProjectSaveResponseDto> getEntProject(int entId) {
        Ent ent = entRepository.findById(entId).orElse(null);

        if (ent == null) {
            throw new IllegalArgumentException("Invalid entId.");
        }

        List<Project> projects = projectRepository.findByEnt(ent);
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

    public List<ProjectInvitedResponseDto> getInvitedList(Integer userId) {
        List<ProjectInvitedResponseDto> invitedProjectDtos = new ArrayList<>();

        List<ProjectInvited> invitedProjects = projectInvitedRepository.findByUserUserId(userId);
        System.out.println(invitedProjects);
        List<Integer> projectIds = new ArrayList<>();

        for (ProjectInvited invited : invitedProjects) {
            projectIds.add(invited.getProject().getProjectId());
        }

        List<Project> projects = projectRepository.findByProjectIdIn(projectIds);

        for (Project project : projects) {
            // 프로젝트 초대 정보 조회
            ProjectInvited invited = invitedProjects.stream()
                    .filter(inv -> inv.getProject().getProjectId().equals(project.getProjectId()))
                    .findFirst()
                    .orElse(null);

            if (invited != null) {
                ProjectInvitedResponseDto projectDto = ProjectInvitedResponseDto.builder()
                        .projectId(project.getProjectId())
                        .entId(project.getEnt().getEntId())
                        .createdAt(invited.getCreatedAt())
                        // 나머지 필드도 동일하게 설정
                        .build();

                invitedProjectDtos.add(projectDto);
            }
        }

        return invitedProjectDtos;
    }

}

package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.config.AmazonS3Service;
import com.ssafy.singstreet.ent.db.entity.Ent;
import com.ssafy.singstreet.ent.db.repo.EntRepository;
import com.ssafy.singstreet.project.db.entity.*;
import com.ssafy.singstreet.project.db.repo.*;
import com.ssafy.singstreet.project.model.*;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private final AmazonS3Service amazonS3Service;


    // 가져온 태그리스트를 태그테이블에 넣어주기
    public void saveTagList(String[] tagList, Project projectId){ // tag 생성
//        for (String tag : tagList) {
//            tagRepository.save(ProjectTag
//                    .builder()
//                    .projectId(projectId)
//                    .tagName(tag)
//                    .build());
//        }
        for(int i = 1; i < tagList.length; i++) {
            tagRepository.save(ProjectTag
                    .builder()
                    .projectId(projectId)
                    .tagName(tagList[i])
                    .build());
        }
    }

    // 프로젝트 생성
    public Project createProject(ProjectSaveRequestDto dto, MultipartFile file) {
        // Ent와 User 엔티티를 가져옴
        Ent ent = entRepository.findById(dto.getEntId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        String s3Url = amazonS3Service.uploadFile(file);

        // ProjectSaveRequestDto를 Project 엔티티로 변환하고, Ent와 User를 설정
        Project project = Project.builder()
                .ent(ent)
                .user(user)
                .projectName(dto.getProjectName())
                .singerName(dto.getSingerName())
                .singName(dto.getSingName())
                .projectInfo(dto.getProjectInfo())
                .projectImg(s3Url)
                .isVisible(dto.getIsVisible())
                .isRecruited(dto.getIsRecruited())
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

//        for(int i = 0; i < dto.getPartList().size(); i++){
//           Part part = Part.builder()
//                   .project(projectRepository.findByProjectId(projectId.getProjectId()))
//                   .partName(dto.getPartList().get(i))
//                   .build();
//           partRepository.save(part);
//        }

        if (ent == null || user == null) {
            // entId 또는 userId에 해당하는 Ent 또는 User가 존재하지 않는 경우 처리
            throw new IllegalArgumentException("Invalid entId or userId.");
        }
        return project;
    }

    // 프로젝트 수정
    public Project updateProject(Integer projectId, ProjectSaveRequestDto dto, MultipartFile file) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Invalid projectId."));

        String s3Url = amazonS3Service.uploadFile(file);

        // Project 엔티티의 update 메서드 호출하여 필드 업데이트
        project.update(
                dto.getProjectName(),
                dto.getSingerName(),
                dto.getSingName(),
                dto.getProjectInfo(),
                s3Url,
                dto.getIsRecruited(),
                dto.getIsVisible()
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

        partRepository.deleteAll();

//        // 파트 수정
//          for(int i=0; i<dto.getPartList().size(); i++){
//
//            Part part = Part.builder()
//                    .project(project)
//                    .partName(dto.getPartList().get(i))
//                    .user(dto.getUserList().get(i) != null ? userRepository.findByUserId(dto.getUserList().get(i)) : null)
//                    .build();
//            partRepository.save(part);
//        }

        // 파트 수정
        if (dto.getPartList() != null) {
            for (int i = 0; i < dto.getPartList().size(); i++) {
                String partName = dto.getPartList().get(i);
                User user = null; // 기본적으로는 null 값을 할당

                if (partName != null && !partName.isEmpty()) {
                    if (dto.getUserList() != null && i < dto.getUserList().size() && dto.getUserList().get(i) != null) {
                        user = userRepository.findByUserId(dto.getUserList().get(i));
                    }

                    Part part = Part.builder()
                            .project(project)
                            .partName(partName)
                            .user(user)  // 사용자 정보를 할당하거나 null 값을 유지
                            .build();

                    partRepository.save(part);
                }
            }
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

        List<Project> projects = projectRepository.findByEntAndIsDestroyed(ent, false);
        List<ProjectSaveResponseDto> projectResponseDTOs = new ArrayList<>();

        for (Project project : projects) {
            ProjectSaveResponseDto responseDTO = ProjectSaveResponseDto.builder()
                    .projectId(project.getProjectId())
                    .userId(project.getUser().getUserId())
                    .entId(project.getEnt().getEntId())
                    .projectName(project.getProjectName())
                    .singerName(project.getSingerName())
                    .singName(project.getSingName())
                    .projectInfo(project.getProjectInfo())
                    .projectImg(project.getProjectImg())
                    .isDestroyed(project.isDestroyed())
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

    public ProjectInfoDto getProjectInfoById(Integer projectId) {
        Project project = projectRepository.findByProjectId(projectId);
        List<ProjectTag> projectTags = tagRepository.findAllByProjectId(project);
        List<String> tags = new ArrayList<>();
        for (ProjectTag tag : projectTags) {
            tags.add(tag.getTagName());
        }

        List<ProjectMember> projectMembers = projectMemberRepository.findByProjectMemberId_Project(project);
        List<User> userList = new ArrayList<>();
        for (ProjectMember projectMember : projectMembers) {
            userList.add(projectMember.getUser());
        }

        List<Part> parts = partRepository.findAllByProjectId(projectId);

        List<ProjectPartDto> partList = new ArrayList<>();

        for (Part part : parts) {
            ProjectPartDto dtos = ProjectPartDto.builder()
                    .nickname(part.getUser() != null ? part.getUser().getNickname() : "") // If part.getUser() is null, use an empty string
                    .partName(part.getPartName())
                    .userId(part.getUser() != null ? part.getUser().getUserId() : -1) // 미등록시 -1
                    .build();
            partList.add(dtos);
        }


        ProjectInfoDto projectInfoDtos = ProjectInfoDto.builder()
                .projectId(project.getProjectId())
                .userId(project.getUser().getUserId())
                .projectName(project.getProjectName())
                .singerName(project.getSingerName())
                .singName(project.getSingName())
                .projectInfo(project.getProjectInfo())
                .projectImg(project.getProjectImg())
                .isRecruited(project.isRecruited())
                .partList(partList)
                .tagList(tags)
                .userlist(userList)
                .isVisible(project.isVisible())
                .build();

        return projectInfoDtos;
    }
}

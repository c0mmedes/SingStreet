package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.model.ProjectInfoDto;
import com.ssafy.singstreet.project.model.ProjectInvitedResponseDto;
import com.ssafy.singstreet.project.model.ProjectSaveRequestDto;
import com.ssafy.singstreet.project.model.ProjectSaveResponseDto;
import com.ssafy.singstreet.project.service.ProjectService;
import com.ssafy.singstreet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/project")
@CrossOrigin("*")
public class ProjectController {

    private final ProjectService projectService;
//    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/info/{projectId}")
    public ResponseEntity<ProjectInfoDto> getProjectInfoById(@PathVariable Integer projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ProjectInfoDto projectInfoDto = projectService.getProjectInfoById(projectId);
        // 프로젝트가 성공적으로 조회되었을 때 200 OK 상태코드와 생성된 결과물을 응답
        return new ResponseEntity<>(projectInfoDto, HttpStatus.OK);
    }

    // 프로젝트 생성
    @PostMapping
    public ResponseEntity<Project> createProject(
            @RequestPart(value = "dto", required = false) ProjectSaveRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
//        int userId = userService.getCurrentUserId();
//        dto.updateUserId(userId);
        Project createdProject = projectService.createProject(dto, file);
        // 프로젝트가 성공적으로 생성되었을 때 201 Created 상태코드와 생성된 프로젝트를 응답합니다.
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    // 프로젝트 수정
    @PostMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Integer projectId,
            @RequestPart(value = "dto", required = false) ProjectSaveRequestDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        System.out.println(file);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Project project = projectService.updateProject(projectId, dto, file);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    // 프로젝트 삭제여부 처리
    @PutMapping("/delete/{projectId}")
    public ResponseEntity<Project> deleteProject(@PathVariable Integer projectId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Project project = projectService.deleteProject(projectId);
        if (project != null) {
            return new ResponseEntity<>(project, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 프로젝트 상세조회
    @GetMapping("/detail/{projectId}")
    public ResponseEntity<ProjectSaveResponseDto> getProjectById(@PathVariable Integer projectId) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 필요한 정보만을 ProjectResponseDTO에 담아서 반환
        ProjectSaveResponseDto responseDTO = ProjectSaveResponseDto.builder()
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
                .createdAt(project.getCreatedAt())
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    // 프로젝트 페이징 전체 조회
    @GetMapping
    public ResponseEntity<Page<ProjectSaveResponseDto>> getAllProjects(Pageable pageable) {
        Page<ProjectSaveResponseDto> projectResponsePage = projectService.pageList(pageable);
        if (projectResponsePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectResponsePage, HttpStatus.OK);
    }

    // 프로젝트 페이징 키워드 검색
    @GetMapping("/{keyword}")
    public Page<ProjectSaveResponseDto> getProjectsByKeyword(@PathVariable(name = "keyword") String keyword, Pageable pageable) {
        return projectService.getProjectByKeyword(keyword, pageable);
    }

    // 프로젝트 태그 검색
    @GetMapping("/tag/{tag}")
    public Page<ProjectSaveResponseDto> getProjectByTag(@PathVariable(name = "tag") String tag, Pageable pageable) {
        return projectService.getProjectByTag(tag, pageable);
    }

    // 내 프로젝트 목록
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectSaveResponseDto>> getMyProject(@PathVariable Integer userId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<ProjectSaveResponseDto> projectResponseDTOs = projectService.getMyProject(userId);
        if (projectResponseDTOs == null || projectResponseDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectResponseDTOs, HttpStatus.OK);
    }

    // 엔터 내 프로젝트 목록
    @GetMapping("/ent/{entId}")
    public ResponseEntity<List<ProjectSaveResponseDto>> getEntProject(@PathVariable Integer entId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<ProjectSaveResponseDto> projectResponseDTOs = projectService.getEntProject(entId);
        if (projectResponseDTOs == null || projectResponseDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectResponseDTOs, HttpStatus.OK);
    }

    // 초대된 프로젝트 목록
    @GetMapping("/invitedList/{userId}")
    public ResponseEntity<List<ProjectInvitedResponseDto>> getInvitedList(@PathVariable Integer userId) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<ProjectInvitedResponseDto> projectResponseDTOs = projectService.getInvitedList(userId);
        if (projectResponseDTOs == null || projectResponseDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectResponseDTOs, HttpStatus.OK);
    }
}

package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.model.ProjectSaveRequestDto;
import com.ssafy.singstreet.project.model.ProjectSaveResponseDto;
import com.ssafy.singstreet.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // POST 요청을 처리하는 API
    // 프로젝트 생성
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody ProjectSaveRequestDto dto) {
        Project createdProject = projectService.createProject(dto);

        // 프로젝트가 성공적으로 생성되었을 때 201 Created 상태코드와 생성된 프로젝트를 응답합니다.
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    // 프로젝트 수정
    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@PathVariable Integer projectId, @RequestBody ProjectSaveRequestDto dto) {
        Project project = projectService.updateProject(projectId, dto);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    // 프로젝트 삭제여부 처리
    @PutMapping("/delete/{projectId}")
    public ResponseEntity<Project> deleteProject(@PathVariable Integer projectId, @RequestBody ProjectSaveRequestDto dto) {
        Project project = projectService.deleteProject(projectId, dto);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    // 프로젝트 전체 조회
    @GetMapping
    public ResponseEntity<List<ProjectSaveResponseDto>> getAllProjects() {
        List<ProjectSaveResponseDto> projectResponseDTOs = projectService.getAllProjects();
        if (projectResponseDTOs == null || projectResponseDTOs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(projectResponseDTOs, HttpStatus.OK);
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
                .build();

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}

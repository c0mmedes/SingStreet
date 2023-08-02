package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.entity.ProjectInvited;
import com.ssafy.singstreet.project.model.ProjectInvitedRequestDto;
import com.ssafy.singstreet.project.service.ProjectFileUploadService;
import com.ssafy.singstreet.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/upload")
public class ProjectFileUploadController {

    private final ProjectFileUploadService projectFileUploadService;

    // 음원명 업로드
    @PutMapping("/audio/{projectId}/{audioFileName}")
    public ResponseEntity<Project> updateAudioFile(@PathVariable Integer projectId, @PathVariable String audioFileName) {
        Project project = projectFileUploadService.updateAudioFile(projectId, audioFileName);

        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    // 영상이름 업로드
    @PutMapping("/file/{projectId}/{originFileName}")
    public ResponseEntity<Project> updateOriginFilename(@PathVariable Integer projectId, @PathVariable String originFileName) {
        Project project = projectFileUploadService.updateOriginFilename(projectId, originFileName);

        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

}

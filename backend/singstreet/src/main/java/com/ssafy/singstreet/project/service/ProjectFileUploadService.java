package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectFileUploadService {

    private final ProjectRepository projectRepository;

    public Project updateAudioFile(Integer projectId, String audioFilename) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            project.updateAudioFile(audioFilename);
            return projectRepository.save(project);
        }

        return null; // 프로젝트가 존재하지 않는 경우
    }

    public Project updateOriginFilename(Integer projectId, String originFilename) {
        Project project = projectRepository.findById(projectId).orElse(null);

        if (project != null) {
            project.updateOriginFilename(originFilename);
            return projectRepository.save(project);
        }

        return null; // 프로젝트가 존재하지 않는 경우
    }
}

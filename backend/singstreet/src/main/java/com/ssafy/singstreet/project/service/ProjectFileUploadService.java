package com.ssafy.singstreet.project.service;

import com.ssafy.singstreet.config.AmazonS3Service;
import com.ssafy.singstreet.project.db.entity.Project;
import com.ssafy.singstreet.project.db.repo.ProjectRepository;
import com.ssafy.singstreet.project.model.ProjectFileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ProjectFileUploadService {

    private final ProjectRepository projectRepository;
    private final AmazonS3Service amazonS3Service;

    // 음원 업로드
    public void updateAudioFile(ProjectFileDto dto) throws Exception {
        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow(Exception::new);

        String s3Url = amazonS3Service.uploadFile(dto.getMultipartFile());

        project.updateAudioFile(s3Url);
        projectRepository.save(project);
    }

    public void uploadVideo(MultipartFile file, Integer projectId) {
        System.out.println(projectId);
        Project project = projectRepository.findByProjectId(projectId);

        System.out.println(project);
        String s3Url = amazonS3Service.uploadFile(file);

        project.updateOriginFilename(s3Url);
        projectRepository.save(project);
    }

//    // 음원 다운로드
//    public ResponseEntity<InputStreamResource> downloadFile(String filename, HttpServletRequest request) {
//        InputStream inputStream = amazonS3Service.downloadFileFromS3(filename); // S3에서 파일 다운로드
//
//        String contentType = request.getServletContext().getMimeType(filename);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.parseMediaType(contentType));
//        headers.setContentDispositionFormData("attachment", filename);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(new InputStreamResource(inputStream));
//    }
}

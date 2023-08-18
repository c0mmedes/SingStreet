package com.ssafy.singstreet.project.controller;

import com.ssafy.singstreet.config.AmazonS3Service;
import com.ssafy.singstreet.project.model.ProjectFileDto;
import com.ssafy.singstreet.project.service.ProjectFileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@CrossOrigin("*")
public class ProjectFileController {

    private final ProjectFileUploadService projectFileUploadService;
    private final AmazonS3Service amazonS3Service;

    // 음원 업로드
    @PutMapping("/upload/audio")
    public ResponseEntity<String> updateAudioFile(@RequestBody ProjectFileDto dto) {
        // 토큰 검증 및 인증 실패 처리
        // 액세스 토큰을 추출하여 Spring Security의 SecurityContextHolder를 통해 인증 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            projectFileUploadService.updateAudioFile(dto);
            return ResponseEntity.ok("audio uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error uploading audio");
        }
    }


    @PostMapping("/upload/video/{projectId}")
    public ResponseEntity<String> uploadVideo(
            @RequestPart(value = "file", required = false) MultipartFile file,
            @PathVariable(value = "projectId", required = false) Integer projectId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            projectFileUploadService.uploadVideo(file, projectId);
            return ResponseEntity.ok("Video uploaded successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error uploading video");
        }
    }

//    @GetMapping("/download/{filename}")
//    public ResponseEntity<UrlResource> downloadFile(@PathVariable String filename) {
//        try {
//            return amazonS3Service.downloadImage(filename);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable String filename) {
        try {
            ResponseEntity<InputStreamResource> urlResource = amazonS3Service.downloadFile(filename); // 이 부분 수정 필요

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", filename);

            System.out.println(urlResource);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(new InputStreamResource(urlResource.getBody().getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

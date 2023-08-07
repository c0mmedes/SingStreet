package com.ssafy.singstreet.project.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class ProjectFileDto {
    MultipartFile multipartFile;
    Integer projectId;
}

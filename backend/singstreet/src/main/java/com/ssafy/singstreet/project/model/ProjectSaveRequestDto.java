package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectSaveRequestDto {
    private Integer projectId;
    private Integer entId;
    private Integer userId;
    private String projectName;
    private String singerName;
    private String singName;
    private String projectInfo;
    private String projectImg;
}

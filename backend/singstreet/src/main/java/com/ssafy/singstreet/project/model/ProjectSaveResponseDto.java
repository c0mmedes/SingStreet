package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectSaveResponseDto {
    private Integer projectId;
    private Integer entId;
    private Integer userId;
    private String projectName;
    private String singerName;
    private String singName;
    private String projectInfo;
    private String projectImg;
    private int likeCount;
    private int hitCount;
    private int monthlyLikeCount;
    private boolean isCompleted;
    private boolean isDestroyed;
    private String originFilename;
    private LocalDate lastEnterDate;
}

package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private LocalDate createdAt;

    // createdAt 필드를 원하는 형식으로 포맷하여 반환하는 메서드
    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt.format(formatter);
    }

}

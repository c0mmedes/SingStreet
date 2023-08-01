package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 전체 생성자
@Builder
public class ProjectMemberDto {
    private Boolean isLeader;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private Integer userId;
}

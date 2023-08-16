package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInvitedResponseDto {
    private Integer projectId;
    private Integer entId;
    private LocalDateTime createdAt;
    private Boolean isAccept;
    private String entName;
    private String projectName;
    private Integer projectLeaderId;
    private String projectLeaderNickname;
    private Integer projectMemberId; // 프로젝트 초대시 고유값

    // createdAt 필드를 원하는 형식으로 포맷하여 반환하는 메서드
    public String getFormattedCreatedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return createdAt.format(formatter);
    }
}

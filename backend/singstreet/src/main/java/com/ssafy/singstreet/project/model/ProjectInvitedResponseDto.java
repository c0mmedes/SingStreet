package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
}

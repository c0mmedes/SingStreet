package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInvitedMemberDto {
    private Integer userId;
    private String nickname;
    private LocalDateTime createdAt;
    private String entName;
}

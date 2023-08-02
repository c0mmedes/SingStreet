package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectJoinDto {
    private Integer projectId;
    private Integer userId;
    private Date createdAt;
    private Boolean isAccept;
}

package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRankDto {
    private Integer projectId;
    private String entName;
    private String singerName;
    private String singName;
    private String projectImg;
    private Integer likeCount;
}

package com.ssafy.singstreet.project.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectLikeResponseDto {
    Integer projectId;
    String entName;
    String singerName;
    String singName;
}

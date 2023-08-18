package com.ssafy.singstreet.project.model;

import com.ssafy.singstreet.user.db.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class ProjectInfoDto {
    private Integer projectId;
    private Integer userId; // 프로젝트장
    private String projectName;
    private String singerName;
    private String singName;
    private String projectInfo;
    private String projectImg;
    private Boolean isRecruited; // 마감여부
    private List<ProjectPartDto> partList;
    private List<String> tagList;
    private List<User> userlist;
    private Boolean isVisible;
    private LocalDateTime createdAt;


}

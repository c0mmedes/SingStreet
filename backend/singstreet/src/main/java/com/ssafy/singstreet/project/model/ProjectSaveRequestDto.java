package com.ssafy.singstreet.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectSaveRequestDto {
    private Integer entId;
    private Integer userId;
    private String projectName;
    private String singerName;
    private String singName;
    private String projectInfo;
    private String projectImg;
    private String projectTagList;
    private Boolean isRecruited;
    private Boolean isVisible;
    private List<String> partList;
    private List<Integer> userList;

//    public void updateUserId(int userId){
//        this.userId = userId;
//    }
}

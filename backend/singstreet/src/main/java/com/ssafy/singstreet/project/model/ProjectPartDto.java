package com.ssafy.singstreet.project.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProjectPartDto {
    Integer userId;
    String nickname;
    String partName;
}

package com.ssafy.singstreet.studio.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BlockDto {

    Integer userId;
    Integer blockId;
    Integer projectId;
    float timeLine; // 시간길이
    float xPos; // x좌표
    float yPos; // y좌표
    String blockName;
    int s3Path; // src 주소

}

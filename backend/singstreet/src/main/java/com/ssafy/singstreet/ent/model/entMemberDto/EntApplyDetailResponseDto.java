package com.ssafy.singstreet.ent.model.entMemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntApplyDetailResponseDto {
    private int applId;
    private int entId;
    private int userId;
    private String nickname;
    private String hope;
    private String artist;
    private int age;
    private String content;
    private String audioName;
}

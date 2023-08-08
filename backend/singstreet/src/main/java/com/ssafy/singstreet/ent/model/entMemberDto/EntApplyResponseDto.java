package com.ssafy.singstreet.ent.model.entMemberDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class EntApplyResponseDto {
    private int userId;
    private int applId;
    private String nickname;
    private LocalDateTime createAt;
}

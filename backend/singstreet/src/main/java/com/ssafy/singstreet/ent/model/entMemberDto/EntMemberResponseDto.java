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
public class EntMemberResponseDto {
    private int userId;
    private String nickname;
    private String email;
    private char gender;
    private LocalDateTime createdAt;
}

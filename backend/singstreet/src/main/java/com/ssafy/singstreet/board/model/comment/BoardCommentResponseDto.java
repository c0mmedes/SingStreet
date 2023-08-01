package com.ssafy.singstreet.board.model.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardCommentResponseDto {
    private int userId;
    private String nickname;
    private String userImg;
    private String content;
    private LocalDateTime createdAt;

}

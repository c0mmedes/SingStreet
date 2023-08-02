package com.ssafy.singstreet.board.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDetailResponseDto {
    private int boardId;
    private int userId;
    private String nickname;
    private String userImg;
    private String title;
    private Character type;
    private String content;
    private String answerText;
    private LocalDateTime anseredAt;
}

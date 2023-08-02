package com.ssafy.singstreet.board.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardUpdateRequestDto {

    private int boardId;
    private int userId;
    private String title;
    private String content;
}

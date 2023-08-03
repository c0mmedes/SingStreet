package com.ssafy.singstreet.board.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardRequestDto {
    private int userId;
    private String title;
    private Character type;    //Q:QnA, C:Common
    private String content;
}

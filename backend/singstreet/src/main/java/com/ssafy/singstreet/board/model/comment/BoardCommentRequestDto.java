package com.ssafy.singstreet.board.model.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardCommentRequestDto {
    private int userId;
    private int boardId;
    private String content;

    public void updateUserId(int userId){
        this.userId = userId;
    }
}

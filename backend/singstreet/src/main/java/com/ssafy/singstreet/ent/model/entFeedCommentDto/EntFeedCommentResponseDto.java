package com.ssafy.singstreet.ent.model.entFeedCommentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntFeedCommentResponseDto {
    private int commentId;
    private int userId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

}

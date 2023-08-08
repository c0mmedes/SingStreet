package com.ssafy.singstreet.ent.model.entFeedCommentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EntFeedCommentRequestDto {
    private int feedId;
    private int userId;
    private String content;
}

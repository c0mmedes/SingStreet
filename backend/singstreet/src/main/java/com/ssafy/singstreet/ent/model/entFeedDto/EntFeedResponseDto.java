package com.ssafy.singstreet.ent.model.entFeedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntFeedResponseDto {
    private int feedId;
    private int entId;
    private int userId;
    private String title;
    private String content;
    private boolean isNotice;
    private String filename;
}

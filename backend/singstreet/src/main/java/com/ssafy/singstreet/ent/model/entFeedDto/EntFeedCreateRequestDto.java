package com.ssafy.singstreet.ent.model.entFeedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntFeedCreateRequestDto {
    private int user;
    private int ent;
    private String title;
    private String content;
    private boolean isNotice;
    private String fileName;
}

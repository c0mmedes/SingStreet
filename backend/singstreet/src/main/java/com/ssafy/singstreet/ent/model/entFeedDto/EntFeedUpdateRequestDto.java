package com.ssafy.singstreet.ent.model.entFeedDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EntFeedUpdateRequestDto {
    private int feedId;
    private String title;
    private String content;
    private String fileName;

    @Builder
    public EntFeedUpdateRequestDto(String title, String content, String fileName){
        this.title = title;
        this.content = content;
        this.fileName = fileName;
    }
}

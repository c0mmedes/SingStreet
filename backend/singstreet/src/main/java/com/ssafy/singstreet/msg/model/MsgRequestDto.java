package com.ssafy.singstreet.msg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MsgRequestDto {
    private int receiver;
    private int sender;
    private String title;
    private String content;
    private Integer replyId;
}

package com.ssafy.singstreet.msg.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MsgDetailResponseDto {
    private int msgId;
    private int receiverId;
    private String receiverNickname;
    private int senderId;
    private String senderNickname;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isConfirmed;
    private int replyId;

    public void update(int replyId){
        this.replyId = replyId;
    }
}

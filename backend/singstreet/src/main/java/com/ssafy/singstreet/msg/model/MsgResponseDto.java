package com.ssafy.singstreet.msg.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MsgResponseDto {
    private int msgId;
    private int receiverId;
    private String receiverNickname;
    private int senderId;
    private String senderNickname;
    private String title;
    private LocalDateTime createdAt;
    private Boolean isConfirmed;

}


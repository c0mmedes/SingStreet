package com.ssafy.singstreet.msg.service;

import com.ssafy.singstreet.msg.db.entity.Message;
import com.ssafy.singstreet.msg.db.repo.MessageRepository;
import com.ssafy.singstreet.msg.model.MsgRequestDto;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MsgService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // Create Msg
    @Transactional
    public Boolean createMsg(MsgRequestDto requestDto){
        System.out.println("2");
        Message message = Message.builder()
                .receiver(userRepository.findByUserId(requestDto.getReceiver()))
                .sender(userRepository.findByUserId(requestDto.getSender()))
                .msgTitle(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        System.out.println("1");
        if (requestDto.getReplyId() != null){
            message.updateReplyId(requestDto.getReplyId());
        }

        messageRepository.save(message);
        return true;
    }
}

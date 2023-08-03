package com.ssafy.singstreet.msg.service;

import com.ssafy.singstreet.msg.db.entity.Message;
import com.ssafy.singstreet.msg.db.repo.MessageRepository;
import com.ssafy.singstreet.msg.model.MsgDetailResponseDto;
import com.ssafy.singstreet.msg.model.MsgRequestDto;
import com.ssafy.singstreet.msg.model.MsgResponseDto;
import com.ssafy.singstreet.user.db.entity.User;
import com.ssafy.singstreet.user.db.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MsgService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    // Read Confirmed Receive Msg
    public List<MsgResponseDto> readReceiveConfirmedMsgList(int userId){

        User user = userRepository.findByUserId(userId);
        List<Message> messageList = messageRepository.findAllByReceiverAndIsConfirmed(user);

        return messageList.stream().map(this::convertMessageToDto).collect(Collectors.toList());
    }

    //Read Not Confirmed Receive Msg
    public List<MsgResponseDto> readReceiveNotConfirmedMsgList(int userId){

        User user = userRepository.findByUserId(userId);
        List<Message> messageList = messageRepository.findAllByReceiverAndIsNotConfirmed(user);

        return messageList.stream().map(this::convertMessageToDto).collect(Collectors.toList());
    }
    // Read Sender Msg
    public List<MsgResponseDto> readSendMsgList(int userId) {
        User user = userRepository.findByUserId(userId);
        List<Message> messageList = messageRepository.findAllBySender(user);

        return messageList.stream().map(this::convertMessageToDto).collect(Collectors.toList());
    }


    //Read Detail
    public MsgDetailResponseDto readDetail(int msgId){
        Message msg = messageRepository.findById(msgId).orElseThrow(()->
                new IllegalArgumentException("해당 쪽지는 없습니다. msgId"+ msgId));

        msg.updateIsConfirmed();
        messageRepository.save(msg);

        return convertMessageToDetailDto(msg);
    }


    // Delete Msg
    @Transactional
    public Boolean DeleteMsg(int userId, int msgId){
        Message msg = messageRepository.findById(msgId).orElseThrow(()->
                new IllegalArgumentException("해당 쪽지가 없습니다 msgId :" +msgId));

        if(msg.getReceiver().getUserId() == userId){
            msg.updateReceiverDeleted();
        } else if (msg.getSender().getUserId() == userId) {
            msg.updateSenderDeleted();
        }

        messageRepository.save(msg);

        return true;
    }


    // Create Msg
    @Transactional
    public Boolean createMsg(MsgRequestDto requestDto){
        Message message = Message.builder()
                .receiver(userRepository.findByUserId(requestDto.getReceiver()))
                .sender(userRepository.findByUserId(requestDto.getSender()))
                .msgTitle(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        if (requestDto.getReplyId() != null){
            message.updateReplyId(requestDto.getReplyId());
        }

        messageRepository.save(message);
        return true;
    }




    // Convert
    public MsgResponseDto convertMessageToDto(Message msg){
        return MsgResponseDto.builder()
                .msgId(msg.getMsgId())
                .receiverId(msg.getReceiver().getUserId())
                .receiverNickname(msg.getReceiver().getNickname())
                .senderId(msg.getSender().getUserId())
                .senderNickname(msg.getSender().getNickname())
                .title(msg.getMsgTitle())
                .createdAt(msg.getCreatedAt())
                .isConfirmed(msg.getIsConfirmed())
                .build();
    }
    public MsgDetailResponseDto convertMessageToDetailDto(Message msg){
        MsgDetailResponseDto result = MsgDetailResponseDto.builder()
                .msgId(msg.getMsgId())
                .receiverId(msg.getReceiver().getUserId())
                .receiverNickname(msg.getReceiver().getNickname())
                .senderId(msg.getSender().getUserId())
                .senderNickname(msg.getSender().getNickname())
                .title(msg.getMsgTitle())
                .content(msg.getContent())
                .createdAt(msg.getCreatedAt())
                .isConfirmed(msg.getIsConfirmed())
                .build();
        if (msg.getReplyId() != null){
            result.update(msg.getReplyId());
        }

        return result;
    }
}

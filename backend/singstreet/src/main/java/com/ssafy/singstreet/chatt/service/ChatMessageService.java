package com.ssafy.singstreet.chatt.service;

import com.ssafy.singstreet.chatt.db.ChatMessage;
import com.ssafy.singstreet.chatt.db.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository messageRepository;

    public void save(ChatMessage message){
        message.updateDate();
        messageRepository.save(message);
    }

    public List<ChatMessage> getAll(){
        return messageRepository.findAll();
    }

    public Slice<ChatMessage> getMessagesWithPagination(int entId,int page, int size){
        return messageRepository.findAllByEntId(entId,PageRequest.of(page,size,Sort.by("createdAt")));
    }
}

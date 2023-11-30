package com.example.demo.chatt.service;

import com.example.demo.chatt.db.ChatMessage;
import com.example.demo.chatt.db.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageService {
    @Autowired
    ChatMessageRepository messageRepository;

    public void save(ChatMessage message){
        message.updateDate();
        messageRepository.save(message);
    }

    public List<ChatMessage> getAll(){
        return messageRepository.findAll();
    }

    public Slice<ChatMessage> getMessagesWithPagination(int entId,int page, int size){
//        return messageRepository.findAllByEntIdOrderByCreatedAtDesc(entId,PageRequest.of(page,size,Sort.by("createdAt")));
        return messageRepository.findAllByEntId(entId,PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"createdAt")));
    }
}

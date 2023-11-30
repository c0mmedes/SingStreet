package com.example.demo.chatt.db;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    Slice<ChatMessage> findAllByEntId(int entId, PageRequest createdAt);

//    Slice<ChatMessage> findAllByEntId(int entId, Pageable pageable);

//    Slice<ChatMessage> findAllByEntIdOrderByCreatedAtDesc(int entId, Pageable pageable);



}

//package com.ssafy.singstreet.chatt.db;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Slice;
//import org.springframework.data.mongodb.repository.MongoRepository;
//
//
//public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
//
//    Slice<ChatMessage> findAllByEntId(int entId, Pageable pageable);
//}

package com.ssafy.singstreet.chat.service;

import com.ssafy.singstreet.chat.db.ChatMessage;

import org.springframework.web.socket.WebSocketSession;

public interface WebSocketChat {
    void onOpen(WebSocketSession session, int entId);

    void onClose(WebSocketSession session, int entId) throws Exception;

    void onMessage(ChatMessage message, WebSocketSession session, int entId) throws Exception;
}

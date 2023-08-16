package com.ssafy.singstreet.chatt.service;
import com.ssafy.singstreet.chatt.db.ChatMessage;

import javax.websocket.Session;

public interface WebSocketChat {
    void onOpen(Session session, int entId);

    void onClose(Session session, int entId) throws Exception;

    void onMessage(ChatMessage message, Session session, int entId) throws Exception;
}
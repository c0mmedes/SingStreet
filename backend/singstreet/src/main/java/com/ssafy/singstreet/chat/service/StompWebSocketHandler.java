package com.ssafy.singstreet.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.singstreet.chat.db.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Controller
public class StompWebSocketHandler {

    private static Map<Integer, Set<StompSession>> CHAT_ROOMS = new ConcurrentHashMap<>();

    private static ApplicationContext applicationContext;

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        StompWebSocketHandler.applicationContext = applicationContext;
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @MessageMapping("/chat/{entId}")
    @SendToUser("/queue/reply")
    public ChatMessage handleMessage(ChatMessage message, @DestinationVariable int entId) throws Exception {
        System.out.println("[Chat] 입력된 메세지 입니다. >" + message);
        message.updateDate();

        ChatMessageService messageService = applicationContext.getBean(ChatMessageService.class);
        messageService.save(message);

        Set<StompSession> roomSessions = CHAT_ROOMS.getOrDefault(entId, new CopyOnWriteArraySet<>());
        for (StompSession client : roomSessions) {
            System.out.println("[Chat] 메세지를 전달합니다. > " + message);
            client.send("/topic/chat/" + entId, message);
        }

        return message;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        int entId = 1; // Extract entId from headerAccessor (you need to implement this logic)

        System.out.println("[Chat] 세션이 새로 연결되었습니다. > " + headerAccessor.getSessionId() + ", entId: " + entId);

        StompSession stompSession = (StompSession) headerAccessor.getSessionAttributes().get("session");
        CHAT_ROOMS.computeIfAbsent(entId, k -> new CopyOnWriteArraySet<>()).add(stompSession);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        int entId = 1; // Extract entId from headerAccessor (you need to implement this logic)

        System.out.println("[Chat] 세션을 닫습니다. : " + headerAccessor.getSessionId() + ", entId: " + entId);

        StompSession stompSession = (StompSession) headerAccessor.getSessionAttributes().get("session");
        Set<StompSession> roomSessions = CHAT_ROOMS.get(entId);
        if (roomSessions != null) {
            roomSessions.remove(stompSession);
        }
    }
}

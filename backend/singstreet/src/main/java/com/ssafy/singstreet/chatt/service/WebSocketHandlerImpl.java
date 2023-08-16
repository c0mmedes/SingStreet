package com.ssafy.singstreet.chatt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.singstreet.chatt.config.MessageDecoder;
import com.ssafy.singstreet.chatt.db.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.*;


@ServerEndpoint(value = "/chatt/{entId}", decoders = MessageDecoder.class)
@Component
@CrossOrigin("*")
//@RequiredArgsConstructor
public class WebSocketHandlerImpl extends TextWebSocketHandler implements org.springframework.web.socket.WebSocketHandler {
    private static Map<Integer, Set<Session>> CHAT_ROOMS = Collections.synchronizedMap(new HashMap<>());//동시접근 막도록 동기화보장

//    @Autowired
//    private ChatMessageService messageService;

    private static ApplicationContext applicationContext;
    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketHandlerImpl.applicationContext = applicationContext;
    }
    private ObjectMapper objectMapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session, @PathParam("entId") int entId) {
        System.out.println(session.toString());
        if (!CHAT_ROOMS.containsKey(entId)) {
            CHAT_ROOMS.put(entId, new HashSet<>());
        }

        Set<Session> roomSessions = CHAT_ROOMS.get(entId);
        roomSessions.add(session);
//        System.out.println("[Chat] 세션이 새로 연결되었습니다. > " + session + ", entId: " + entId);
    }

    @OnClose
    public void onClose(Session session, @PathParam("entId") int entId) throws Exception {
        Set<Session> roomSessions = CHAT_ROOMS.get(entId);
        if (roomSessions != null) {
            roomSessions.remove(session);
//            System.out.println("[Chat] 세션을 닫습니다. : " + session + ", entId: " + entId);
        }
    }

    @OnMessage
    public void onMessage(ChatMessage message, Session session , @PathParam("entId") int entId) throws Exception {
        System.out.println("[Chat] 입력된 메세지 입니다. >" + message);
        message.updateDate();
        String jsonMessage = objectMapper.writeValueAsString(message); // objectMapper를 선언해야 합니다.
//        System.out.println(messageService);

        ChatMessageService messageService = applicationContext.getBean(ChatMessageService.class);
        messageService.save(message);

        Set<Session> roomSessions = CHAT_ROOMS.get(entId);
        if (roomSessions != null) { //룸이 있으면
            for (Session client : roomSessions) {   //룸에 있는 모든 사람에게
                if (client.isOpen()) {  //세션이 열려있는지 확인 후
                    System.out.println("[Chat] 메세지를 전달합니다. > " + message);
                    client.getBasicRemote().sendText(jsonMessage);
                }
            }
        }
    }
}
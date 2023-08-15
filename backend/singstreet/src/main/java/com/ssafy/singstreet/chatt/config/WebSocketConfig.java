package com.ssafy.singstreet.chatt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

//@ConditionalOnWebApplication
@Configuration  //SpringBoot의 설정클래스임을 나타냄
//WebSocketMessageBrokerConfigurer : 인터페이스를 구현하여 웹소켓 관련 설정 구성
public class WebSocketConfig  {//implements WebSocketMessageBrokerConfigurer

    @Value("${websocket.port}")
    private int websocketPort;

    //Bean - 컨테이너에서 관리되는 객체로 등록
    //WebSocket엔드포인트를 노출시키기위한 빈 -> 이거로 WebSocket엔드포인트를 등록, 관리 가능
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    //ObjectMapper-> Jackson라이브러리를 사용해 JSON데이터를 자바 객체로 매핑하기 위한 빈
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

//    //registerStompEndpoints : STOMP프로토콜을 사용하는 WebSocket엔드포인트 등록함
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry){
//        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
//    }
}

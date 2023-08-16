package com.ssafy.singstreet.chatt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import com.ssafy.singstreet.chatt.service.WebSocketHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.time.LocalDateTime;

//@ConditionalOnWebApplication
@EnableWebSocket//이 어노테이션을 사용하면 스프링 애플리케이션 내에서 WebSocket을 사용할 수 있게 됩니다
@Configuration  //SpringBoot의 설정클래스임을 나타냄
//WebSocketMessageBrokerConfigurer : 인터페이스를 구현하여 웹소켓 관련 설정 구성
public class WebSocketConfig implements WebSocketConfigurer {//implements WebSocketMessageBrokerConfigurer

    //Bean - 컨테이너에서 관리되는 객체로 등록
    //WebSocket엔드포인트를 노출시키기위한 빈 -> 이거로 WebSocket엔드포인트를 등록, 관리 가능
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    //ObjectMapper-> Jackson라이브러리를 사용해 JSON데이터를 자바 객체로 매핑하기 위한 빈
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Java 8 날짜 및 시간 모듈 추가
        return objectMapper;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandlerImpl(), "/backend/chatt/{entId}")
                .setAllowedOrigins("*");
    }

}

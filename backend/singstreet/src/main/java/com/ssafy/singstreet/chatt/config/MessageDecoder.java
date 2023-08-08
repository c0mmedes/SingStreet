package com.ssafy.singstreet.chatt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.singstreet.chatt.db.ChatMessage;
import lombok.RequiredArgsConstructor;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

@RequiredArgsConstructor
public class MessageDecoder implements Decoder.Text<ChatMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ChatMessage decode(String s) throws DecodeException {
        try {
            return objectMapper.readValue(s, ChatMessage.class);
        } catch (Exception e) {
            throw new DecodeException(s, "Failed to decode Message.", e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
        // 초기화 로직 (필요 없을 수 있습니다)
    }

    @Override
    public void destroy() {
        // 정리 로직 (필요 없을 수 있습니다)
    }
}
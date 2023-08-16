package com.ssafy.singstreet.chatt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.singstreet.chatt.db.ChatMessage;
import lombok.RequiredArgsConstructor;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

@RequiredArgsConstructor
public class MessageDecoder implements Decoder.Text<ChatMessage> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ChatMessage decode(String s) throws DecodeException {
        try {
            return objectMapper.readValue(s, ChatMessage.class);
        } catch (IOException e) {
            throw new DecodeException(s, "Failed to decode JSON", e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
//    private static Gson gson = new Gson();
//
//    @Override
//    public ChatMessage decode(String s) throws DecodeException {
//        return gson.fromJson(s, ChatMessage.class);
//    }
//
//    @Override
//    public boolean willDecode(String s) {
//        return (s != null);
//    }
//
//    @Override
//    public void init(EndpointConfig endpointConfig) {
//        // Custom initialization logic
//    }
//
//    @Override
//    public void destroy() {
//        // Close resources
//    }


}
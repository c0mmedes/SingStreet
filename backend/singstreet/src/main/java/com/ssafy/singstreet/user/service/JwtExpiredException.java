package com.ssafy.singstreet.user.service;

public class JwtExpiredException extends Throwable {
    public JwtExpiredException(String message) {
        super(message);
    }
}

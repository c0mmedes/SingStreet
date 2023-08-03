package com.ssafy.singstreet.user.model;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String email;
    private String password;
}

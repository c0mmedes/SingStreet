package com.ssafy.singstreet.user.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class UserRegistDTO {
    private String nickname;
    private String userImg; // MultipartFile로 수정
    private String email;
    private Character gender;
    private String password;
}

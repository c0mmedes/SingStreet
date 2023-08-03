package com.ssafy.singstreet.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailDTO {
    private String nickname;
    private String userImg;
    private String email;
    private Character gender;
    private int userId;
}

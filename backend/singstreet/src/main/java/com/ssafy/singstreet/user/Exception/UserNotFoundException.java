package com.ssafy.singstreet.user.Exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String email){
        System.out.println(email+"은 등록된 이메일이 아닙니다.");
    }
}

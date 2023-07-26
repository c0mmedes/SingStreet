package com.ssafy.singstreet.user.Exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(String text){
        System.out.println(text);
    }
}

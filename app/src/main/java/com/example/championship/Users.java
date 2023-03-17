package com.example.championship;

public class Users {

    private String id;
    private String email;
    private String password;
    private String nickName;
    private String avatar;
    private String token;

    public Users(String Email, String Password) {

        email = Email;
        password = Password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAvatar() {
        return avatar;
    }
}
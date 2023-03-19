package com.example.championship;

public class Users {

    private String id;
    private String email;
    private String password;
    private String nickName;
    private String avatar;
    private String token;

    public Users() {
    }

    public Users(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
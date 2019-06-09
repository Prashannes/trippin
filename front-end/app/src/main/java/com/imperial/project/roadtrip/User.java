package com.imperial.project.roadtrip;

import java.io.Serializable;

public class User implements Serializable {
    public String getEmail() {
        return email;
    }

    public String getNickname() {
        return nickname;
    }

    private String email;
    private String password;
    private String nickname;

    public User() {

    }

    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public User(String email, String nickname) {
        this.email = email;
        this.password = "";
        this.nickname = nickname;
    }
}

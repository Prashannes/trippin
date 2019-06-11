package com.imperial.project.roadtrip.data;

import java.io.Serializable;

public class User implements Serializable {
    private String username;

    public String getUsername() {
        return username;
    }

    public User(String username) {
        this.username = username;
    }
}

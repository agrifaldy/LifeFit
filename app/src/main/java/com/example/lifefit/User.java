package com.example.lifefit;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String password;
    public String username;
    public String email;
    public String userId;
    public String nomorTelepon;
    public String pekerjaan;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userId, String username, String email, String password, String nomorTelepon, String pekerjaan) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.nomorTelepon = nomorTelepon;
        this.pekerjaan = pekerjaan;
    }
}

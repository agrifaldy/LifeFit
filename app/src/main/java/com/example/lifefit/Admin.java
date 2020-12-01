package com.example.lifefit;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
    public class Admin {

        public String username;
        public String email;

        public Admin() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Admin(String username, String email) {
            this.username = username;
            this.email = email;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("username", username);
            result.put("email", email);

            return result;
        }


    }

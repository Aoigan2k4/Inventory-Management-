package com.example.inventorymanagementproject;

import java.util.ArrayList;
import java.util.List;

import com.example.inventorymanagementproject.Builder.Items;

public class User {
    private final String id;
    private String username;
    private String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.username = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}


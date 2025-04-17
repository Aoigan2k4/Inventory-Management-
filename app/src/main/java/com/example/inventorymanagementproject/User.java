package com.example.inventorymanagementproject;

public  class User {
    private final String id;
    private String username;
    private String email;
    private String role;

    public User(String id, String name, String email, String role) {
        this.id = id;
        this.username = name;
        this.email = email;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
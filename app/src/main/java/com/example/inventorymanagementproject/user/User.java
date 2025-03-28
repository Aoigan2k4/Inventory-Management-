package com.example.inventorymanagementproject.user;



public class User {
    private final String id;
    private String username;
    private String email;
    private UserRole role; // New attribute

    public User(String id, String name, String email) {
        this.id = id;
        this.username = name;
        this.email = email;
        this.role = UserRole.READ_ONLY; // default or assigned externally
    }

    // Existing getters/setters

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}

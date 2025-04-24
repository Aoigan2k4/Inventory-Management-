package com.example.inventorymanagementproject.COR;

public class ValidationManager {
    String id;
    String username;
    public String email;
    public String password;
    public String role;

    public ValidationManager(String email, String password,  String role){
        this.email = email;
        this.password = password;
        this.role = role;
    }
}


package com.example.inventorymanagementproject.chain;

public class PasswordValidator extends InputHandler {
    @Override
    public boolean handleRequest(String input) {
        // Example: password must be at least 6 characters
        if (input.length() < 6) {
            return false;
        }
        return super.handleRequest(input);
    }
}

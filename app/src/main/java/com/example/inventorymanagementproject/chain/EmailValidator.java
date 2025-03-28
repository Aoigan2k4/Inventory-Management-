package com.example.inventorymanagementproject.chain;

public class EmailValidator extends InputHandler {
    @Override
    public boolean handleRequest(String input) {
        // A very basic check for '@' presence
        if (!input.contains("@")) {
            return false;
        }
        return super.handleRequest(input);
    }
}

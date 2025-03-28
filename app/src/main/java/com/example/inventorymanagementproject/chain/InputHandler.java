package com.example.inventorymanagementproject.chain;

public abstract class InputHandler {
    protected InputHandler nextHandler;

    public void setNextHandler(InputHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public boolean handleRequest(String input) {
        // If this handler can't handle or it fails, it returns false
        // Otherwise, if it succeeds, pass to the next handler if present
        if (nextHandler != null) {
            return nextHandler.handleRequest(input);
        }
        return true;
    }
}

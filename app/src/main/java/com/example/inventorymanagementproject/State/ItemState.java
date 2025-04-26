package com.example.inventorymanagementproject.State;

public enum ItemState {
    AVAILABLE("Available"),
    UNAVAILABLE("Out of Stock");

    private final String status;

    ItemState(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static ItemState setState(int quantity) {
        return quantity > 0 ? AVAILABLE : UNAVAILABLE;
    }
}

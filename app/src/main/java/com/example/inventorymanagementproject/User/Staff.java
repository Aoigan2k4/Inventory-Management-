package com.example.inventorymanagementproject.User;

public class Staff extends User{
    public Staff(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    protected void filterItems() {

    }
}

package com.example.inventorymanagementproject.Users;


public class Admin extends  User {
    public Admin(String id, String name, String email) {
        super(id, name, email);


    }
    @Override
    protected void filterItems() {

    }
}

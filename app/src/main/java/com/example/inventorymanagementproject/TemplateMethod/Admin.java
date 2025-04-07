package com.example.inventorymanagementproject.TemplateMethod;


public class Admin extends User {
    public Admin(String id, String name, String email) {
        super(id, name, email);


    }
    @Override
    protected void filterItems() {

    }
}

package com.example.inventorymanagementproject.TemplateMethod;

public class Client extends User{
    public Client(String id, String name, String email) {
        super(id, name, email);
    }

    @Override
    protected void filterItems() {

    }
}

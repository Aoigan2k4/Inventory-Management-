package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard extends AppCompatActivity {

    Button update, view, add, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        add = findViewById(R.id.AddItems);
        view = findViewById(R.id.ViewInventory);
        logout = findViewById(R.id.LogOut);

        view.setOnClickListener(v -> View());
        add.setOnClickListener(v -> Add());
    }

    private void View() {
        Intent intent = new Intent(Dashboard.this, InventoryListActivity.class);
        startActivity(intent);
        finish();
    }

     private void Add() {
        Intent intent = new Intent(Dashboard.this, InventoryActivity.class);
        startActivity(intent);
    }
}
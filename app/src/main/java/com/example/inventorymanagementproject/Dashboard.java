package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    Button view, add, logout;
    FirebaseManager mng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        add = findViewById(R.id.AddItems);
        view = findViewById(R.id.ViewInventory);
        logout = findViewById(R.id.LogOut);

        view.setOnClickListener(v -> View());
        add.setOnClickListener(v -> Add());
        logout.setOnClickListener(v -> LogOut());
    }

    private void View() {
        Intent intent = new Intent(Dashboard.this, InventoryListActivity.class);
        startActivity(intent);
        finish();
    }

    private void Add() {
        Intent intent = new Intent(Dashboard.this, InventoryActivity.class);
        startActivity(intent);
        finish();
    }

    private void LogOut() {
        mng = FirebaseManager.getInstance();
        mng.getAuth().signOut();

        Intent intent = new Intent(Dashboard.this, LogIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.TemplateMethod.AdminAuthorization;
import com.example.inventorymanagementproject.TemplateMethod.ClientAuthorization;
import com.example.inventorymanagementproject.TemplateMethod.RoleAuthorization;
import com.example.inventorymanagementproject.TemplateMethod.StaffAuthorization;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    Button view, add, logout;
    FirebaseManager mng;
    String role;
    RoleAuthorization authorization;
    Intent intent;
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

        intent = getIntent();
        role = intent.getStringExtra("role");

        assert role != null;
        if (role.equals("Admin")) {
            authorization = new AdminAuthorization();
        } else if (role.equals("Staff")) {
            authorization = new StaffAuthorization();
        } else {
            authorization = new ClientAuthorization();
        }

        authorization.ConfigureAuth(role, this);
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
package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.Inventory.InventoryActivity;
import com.example.inventorymanagementproject.Inventory.InventoryListActivity;
import com.example.inventorymanagementproject.TemplateMethod.AdminAuthorization;
import com.example.inventorymanagementproject.TemplateMethod.ClientAuthorization;
import com.example.inventorymanagementproject.TemplateMethod.RoleAuthorization;
import com.example.inventorymanagementproject.TemplateMethod.StaffAuthorization;
import com.example.inventorymanagementproject.UserList.UserListView;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    Button view, add, logout, adduser, viewusers;
    FirebaseManager mng;
    FirebaseAuth mAuth;
    String role, password, email;
    RoleAuthorization authorization;
    Intent intent;
    TextView greetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        add = findViewById(R.id.AddItems);
        view = findViewById(R.id.ViewInventory);
        logout = findViewById(R.id.LogOut);
        adduser = findViewById(R.id.AddUser);
        viewusers = findViewById(R.id.ViewUsers);
        greetings = findViewById(R.id.Greetings);

        view.setOnClickListener(v -> View());
        add.setOnClickListener(v -> Add());
        viewusers.setOnClickListener(v -> ViewUser());
        logout.setOnClickListener(v -> LogOut());
        adduser.setOnClickListener(v -> AddUser());

        mng = FirebaseManager.getInstance();
        mAuth = mng.getAuth();

        intent = getIntent();
        role = intent.getStringExtra("role");
        greetings.setText("Welcome " + role + ", " + mAuth.getCurrentUser().getDisplayName());

        password = intent.getStringExtra("password") == null ? "" : intent.getStringExtra("password");
        email = intent.getStringExtra("email") == null ? "" : intent.getStringExtra("email");

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

    private void ViewUser() {
        Intent intent = new Intent(Dashboard.this, UserListView.class);
        startActivity(intent);
        finish();
    }

    private void AddUser() {
        Intent intent = new Intent(Dashboard.this, AddUser.class);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    private void LogOut() {
        mng = FirebaseManager.getInstance();
        mng.getAuth().signOut();

        Intent intent = new Intent(Dashboard.this, LogIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
package com.example.inventorymanagementproject.UserList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.inventorymanagementproject.Builder.Item;
import com.example.inventorymanagementproject.Facade.InventoryFacade;
import com.example.inventorymanagementproject.Facade.UserListFacade;
import com.example.inventorymanagementproject.FirebaseManager;
import com.example.inventorymanagementproject.ForgotPass;
import com.example.inventorymanagementproject.Inventory.InventoryDetailActivity;
import com.example.inventorymanagementproject.LogIn;
import com.example.inventorymanagementproject.R;
import com.example.inventorymanagementproject.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserListDetail extends AppCompatActivity {
    private EditText editUserName, editEmail, editRole;
    private Button btnBack, btnDelete;
    private UserListFacade userListFacade;
    private FirebaseFirestore db;
    private FirebaseManager mng;
    private String userId, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        editUserName = findViewById(R.id.editUserName);
        editEmail = findViewById(R.id.editUserEmail);
        editRole = findViewById(R.id.editUserRole);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        userListFacade = new UserListFacade();
        mng = FirebaseManager.getInstance();
        db = mng.getDb();

        userId = getIntent().getStringExtra("userId");
        role = getIntent().getStringExtra("role");

        SharedPreferences prefs = getSharedPreferences("roles", Context.MODE_PRIVATE);
        String adminUsername = prefs.getString("username", null);
        String adminPass = prefs.getString("password", null);

        btnBack.setOnClickListener(v -> back());
        btnDelete.setOnClickListener(v -> deleteUser());

        if(userId != null) {
            loadUser(userId);
        }
        else {
            Toast.makeText(UserListDetail.this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadUser(String userId) {
        db.collection("Users").document(role).collection(role)
                .document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if(documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if(user != null) {
                           editUserName.setText(user.getUsername());
                           editEmail.setText(user.getEmail());
                           editRole.setText(user.getRole());
                        }
                    }
                    else {
                        Toast.makeText(UserListDetail.this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(UserListDetail.this, "Failed to load users", Toast.LENGTH_SHORT).show();
                });
    }

    private void deleteUser() {
        User user = new User();
        user.setId(userId);
        user.setRole(role);
        user.setEmail(editEmail.getText().toString().trim());
        user.setUsername(editUserName.getText().toString().trim());

        SharedPreferences prefs = getSharedPreferences("roles", Context.MODE_PRIVATE);
        String adminUsername = prefs.getString("username", null);

        if(user.getUsername().equals(adminUsername)) {
            Toast.makeText(UserListDetail.this, "Cannot delete admin account!", Toast.LENGTH_SHORT).show();
            return;
        }

        userListFacade.deleteUser(user, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(UserListDetail.this, "User deleted!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UserListDetail.this, UserListView.class);
                startActivity(intent);
                finish();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserListDetail.this, "Delete failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void back() {
        Intent intent = new Intent(UserListDetail.this, UserListView.class);
        startActivity(intent);
        finish();
    }
}
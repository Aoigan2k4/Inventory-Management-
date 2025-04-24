package com.example.inventorymanagementproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddUser extends AppCompatActivity {
    EditText usernameTxt, passTxt, emailTxt;
    Button signUpBtn;
    RadioGroup roles;
    RadioButton admin, staff, client;
    FirebaseManager mng;
    String adminPass, adminUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        usernameTxt = findViewById(R.id.Username);
        passTxt = findViewById(R.id.Password);
        emailTxt = findViewById(R.id.Email);
        signUpBtn = findViewById(R.id.SignUpBtn);
        roles = findViewById(R.id.roleRadioGroup);
        admin = findViewById(R.id.adminRadioButton);
        staff = findViewById(R.id.staffRadioButton);
        client = findViewById(R.id.clientRadioButton);

        SharedPreferences prefs = getSharedPreferences("roles", Context.MODE_PRIVATE);
        adminUsername = prefs.getString("username", null);
        adminPass = prefs.getString("password", null);

        signUpBtn.setOnClickListener(v -> SignUpBtn());
    }

    private void SignUpBtn() {
        String username = usernameTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();
        String selectedRole;

        int selectedRoleId = roles.getCheckedRadioButtonId();

        if (selectedRoleId == -1) {
            selectedRole = "Client";
        } else {
            RadioButton selectedRadioButton = findViewById(selectedRoleId);
            selectedRole = selectedRadioButton.getText().toString();
        }

        if (adminPass != null && adminUsername != null) {
            mng = FirebaseManager.getInstance();
            mng.CreateUser(this, selectedRole, password, username, email, adminPass, adminUsername, true);
        }
        else {
            Toast.makeText(AddUser.this, "Admin credentials not found.", Toast.LENGTH_SHORT).show();
        }
    }
}
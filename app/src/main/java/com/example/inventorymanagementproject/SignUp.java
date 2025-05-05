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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    EditText usernameTxt, passTxt, emailTxt;
    Button signUpBtn;
    TextView login;
    FirebaseManager mng;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameTxt = findViewById(R.id.Username);
        passTxt = findViewById(R.id.Password);
        emailTxt = findViewById(R.id.Email);
        signUpBtn = findViewById(R.id.SignUpBtn);
        login = findViewById(R.id.loginLink);

        signUpBtn.setOnClickListener(v -> SignUpBtn());
        login.setOnClickListener(v -> LogInLink());
    }

    private void SignUpBtn() {
        String username = usernameTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();
        mng = FirebaseManager.getInstance();
        db = mng.getDb();

        com.google.firebase.firestore.Query query = db
                .collection("Users")
                .document("Client")
                .collection("Client")
                .whereEqualTo("username", username);


        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignUp.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        query.get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        return;
                    }
                    Toast.makeText(SignUp.this, "Username already exists", Toast.LENGTH_LONG).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SignUp.this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
                });


        if (!email.contains("@")) {
            Toast.makeText(this, "Please enter valid email address", Toast.LENGTH_LONG).show();
            return;
        }


        if (password.length() < 8) {
            Toast.makeText(this, "Password must be at least 8 characters long", Toast.LENGTH_LONG).show();
            return;
        }

        mng.CreateUser(this, "Client", password, username, email, "", "", false);
    }

    private void LogInLink(){
        Intent intent = new Intent(SignUp.this, LogIn.class);
        startActivity(intent);
        finish();
    }
}
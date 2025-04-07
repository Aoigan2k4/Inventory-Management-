package com.example.inventorymanagementproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {
    EditText emailTxt;
    Button sendEmailBtn;
    FirebaseManager mng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        emailTxt = findViewById(R.id.emailTxt);
        sendEmailBtn = findViewById(R.id.sendButton);

        sendEmailBtn.setOnClickListener(v -> sendEmail());
    }

    private void sendEmail() {
        String email = emailTxt.getText().toString().trim();
        mng = FirebaseManager.getInstance();
        mng.getAuth().sendPasswordResetEmail(email);
    }
}
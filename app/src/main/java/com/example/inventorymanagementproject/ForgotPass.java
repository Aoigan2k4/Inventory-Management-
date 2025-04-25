package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
    ImageView xBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        emailTxt = findViewById(R.id.emailTxt);
        sendEmailBtn = findViewById(R.id.sendBtn);
        xBtn = findViewById(R.id.btnClose);

        xBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
            finish();
        });
        sendEmailBtn.setOnClickListener(v -> sendEmail());
    }

    private void sendEmail() {
        String email = emailTxt.getText().toString().trim();
        mng = FirebaseManager.getInstance();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_LONG).show();
            return;
        }

        if (!email.contains("@")) {
            Toast.makeText(this, "Please enter valid email address", Toast.LENGTH_LONG).show();
            return;
        }

        mng.getAuth().sendPasswordResetEmail(email);
        Toast.makeText(this, "Email sent to this address "  + email, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}
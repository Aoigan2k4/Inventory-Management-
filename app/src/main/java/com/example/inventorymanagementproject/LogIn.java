package com.example.inventorymanagementproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    EditText emailTxt, passTxt;
    RadioGroup roles;
    RadioButton admin, staff, client;
    Button loginBtn;
    TextView signUp, forgotPass;
    FirebaseManager mng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpLink);
        roles = findViewById(R.id.roleRadioGroup);
        admin = findViewById(R.id.adminRadioButton);
        staff = findViewById(R.id.staffRadioButton);
        client = findViewById(R.id.clientRadioButton);
        forgotPass = findViewById(R.id.forgotPassword);

        signUp.setOnClickListener(v -> signUp());
        forgotPass.setOnClickListener(v -> forgotPass());
        loginBtn.setOnClickListener(v -> LogInBtn());
    }

    private void LogInBtn() {
        String email = emailTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();
        int selectedRoleId = roles.getCheckedRadioButtonId();

        if (selectedRoleId == -1) {
            Toast.makeText(LogIn.this, "Please select a role.", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton selectedRoleButton = findViewById(selectedRoleId);
        String role = selectedRoleButton.getText().toString();

        mng = FirebaseManager.getInstance();
        mng.LogInUser(this, email, password, role);
    }

    private void signUp() {
        Intent intent = new Intent(LogIn.this, SignUp.class);
        startActivity(intent);
        finish();
    }

    private void forgotPass() {
        Intent intent = new Intent(LogIn.this, ForgotPass.class);
        startActivity(intent);
        finish();
    }
}

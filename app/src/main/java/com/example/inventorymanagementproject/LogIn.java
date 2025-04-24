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

import com.example.inventorymanagementproject.COR.FirebaseLogInHandler;
import com.example.inventorymanagementproject.COR.InputCheckHandler;
import com.example.inventorymanagementproject.COR.ValidationHandler;
import com.example.inventorymanagementproject.COR.ValidationInterface;
import com.example.inventorymanagementproject.COR.ValidationManager;

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
        Context context = this;
        mng = FirebaseManager.getInstance();

        if (selectedRoleId == -1) {
            Toast.makeText(LogIn.this, "Please select a role.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRoleButton = findViewById(selectedRoleId);
        String role = selectedRoleButton.getText().toString();

        ValidationManager request = new ValidationManager(email, password, role);
        request.email = email;
        request.password = password;
        request.role = role;

        ValidationHandler inputCheck = new InputCheckHandler();
        ValidationHandler fbHandler = new FirebaseLogInHandler();

        inputCheck.setNext(fbHandler);

        inputCheck.handle(context, request, mng, new ValidationInterface() {
            @Override
            public void onSuccess(String message) {}

            @Override
            public void onFailure(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });

        SharedPreferences prefs = getSharedPreferences("roles", Context.MODE_PRIVATE);
        prefs.edit().putString("role", role).apply();

        if (role.equals("Admin")) {
            prefs.edit().putString("email", email).apply();
            prefs.edit().putString("password", password).apply();
        }
        else {
            prefs.edit().putString("email", null).apply();
            prefs.edit().putString("password", null).apply();
        }
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

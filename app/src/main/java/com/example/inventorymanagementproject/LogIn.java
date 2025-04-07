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
    TextView signUp;
    FirebaseAuth mAuth;
    FirebaseManager mng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Initialize UI components
        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpLink);
        roles = findViewById(R.id.roleRadioGroup);
        admin = findViewById(R.id.adminRadioButton);
        staff = findViewById(R.id.staffRadioButton);
        client = findViewById(R.id.clientRadioButton);

        // Set up SignUp link click
        signUp.setOnClickListener(v -> signUp());

        // Login button click event
        loginBtn.setOnClickListener(view -> {
            String email = emailTxt.getText().toString().trim();
            String password = passTxt.getText().toString().trim();
            mng = FirebaseManager.getInstance();
            mAuth = mng.getAuth();

            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogIn.this, task -> {
                            if (task.isSuccessful()) {
                                // Get selected role from the radio group
                                int selectedRoleId = roles.getCheckedRadioButtonId();
                                if (selectedRoleId == -1) {
                                    // No role selected: either show an error or assign a default
                                    Toast.makeText(LogIn.this, "Please select a role.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                RadioButton selectedRoleButton = findViewById(selectedRoleId);
                                String role = selectedRoleButton.getText().toString(); // e.g., "Admin", "Staff", "Client"

                                FirebaseUser loggedInUser = mAuth.getCurrentUser();
                                // Navigate to InventoryActivity and pass the role
                                Intent intent = new Intent(LogIn.this, Dashboard.class);

                                intent.putExtra("role", role);
                                startActivity(intent);
                                finish();
                            } else {
                                Exception exception = task.getException();
                                Log.w(TAG, "signInWithEmail:failure", exception);
                                if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(LogIn.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                    Toast.makeText(LogIn.this, "User not found.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(LogIn.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signUp() {
        Intent intent = new Intent(LogIn.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}

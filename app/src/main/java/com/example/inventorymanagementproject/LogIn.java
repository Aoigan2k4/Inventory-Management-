package com.example.inventorymanagementproject;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.chain.EmailValidator;
import com.example.inventorymanagementproject.chain.EmptyFieldValidator;
import com.example.inventorymanagementproject.chain.InputHandler;
import com.example.inventorymanagementproject.chain.PasswordValidator;
import com.example.inventorymanagementproject.user.User;
import com.example.inventorymanagementproject.user.UserRole;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogIn extends AppCompatActivity {

    private EditText emailTxt, passTxt;
    private Button loginBtn;
    private TextView signUp;
    private FirebaseAuth mAuth;
    private FirebaseManager mng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Initialize UI components
        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpLink);

        // "Sign Up" text click -> go to SignUp activity
        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, SignUp.class);
            startActivity(intent);
            finish();
        });

        // Click "Login" button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString().trim();
                String password = passTxt.getText().toString().trim();

                // 1) Validate inputs via Chain of Responsibility
                if (!validateInputs(email, password)) {
                    // If validation fails, stop here
                    return;
                }

                // 2) Sign in with Firebase
                mng = FirebaseManager.getInstance();
                mAuth = mng.getAuth();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser loggedInUser = mAuth.getCurrentUser();
                                    if (loggedInUser != null) {
                                        // 3) Check user role in Firestore
                                        fetchUserRoleAndRedirect(loggedInUser.getUid());
                                    } else {
                                        Toast.makeText(LogIn.this,
                                                "Could not retrieve user details.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // 4) Handle Firebase exceptions
                                    Exception exception = task.getException();
                                    Log.w(TAG, "signInWithEmail:failure", exception);

                                    if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(LogIn.this,
                                                "Invalid email or password.",
                                                Toast.LENGTH_SHORT).show();
                                    } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                        Toast.makeText(LogIn.this,
                                                "User not found.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LogIn.this,
                                                "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });
    }

    /**
     * Validates email and password using Chain of Responsibility.
     */
    private boolean validateInputs(String email, String password) {
        InputHandler emptyCheck = new EmptyFieldValidator();
        InputHandler emailCheck = new EmailValidator();
        InputHandler passCheck = new PasswordValidator();

        // Build the chain
        emptyCheck.setNextHandler(emailCheck);
        emailCheck.setNextHandler(passCheck);

        // Validate email
        if (!emptyCheck.handleRequest(email)) {
            Toast.makeText(this, "Email is invalid or empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Validate password
        if (!emptyCheck.handleRequest(password)) {
            Toast.makeText(this, "Password is invalid or empty.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Fetch user document from Firestore, read the 'role', and redirect accordingly.
     */
    private void fetchUserRoleAndRedirect(String userId) {
        FirebaseFirestore db = mng.getDb();
        db.collection("Users")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc != null && doc.exists()) {
                            // Convert document to User model
                            User appUser = doc.toObject(User.class);
                            if (appUser != null) {
                                UserRole role = appUser.getRole();
                                redirectBasedOnRole(role);
                            } else {
                                Toast.makeText(LogIn.this,
                                        "User data not found in Firestore.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If there's no doc, default to read-only or show error
                            Toast.makeText(LogIn.this,
                                    "No user record found in Firestore.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LogIn.this,
                                "Failed to fetch user data.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Redirects user to the appropriate screen based on role.
     * For simplicity, we redirect everyone to the same Dashboard.
     * You could differentiate: AdminDashboard, EditorDashboard, etc.
     */
    private void redirectBasedOnRole(UserRole role) {
        switch (role) {
            case ADMIN:
                // e.g., Start an AdminDashboard activity
                Toast.makeText(this, "Welcome, Admin!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogIn.this, Dashboard.class));
                finish();
                break;

            case INVENTORY_EDITOR:
                // e.g., Start a normal Dashboard but enable editing features
                Toast.makeText(this, "Welcome, Inventory Editor!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogIn.this, Dashboard.class));
                finish();
                break;

            case READ_ONLY:
            default:
                // e.g., Start a normal Dashboard but hide editing features
                Toast.makeText(this, "Welcome, Read-Only User!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LogIn.this, Dashboard.class));
                finish();
                break;
        }
    }
}

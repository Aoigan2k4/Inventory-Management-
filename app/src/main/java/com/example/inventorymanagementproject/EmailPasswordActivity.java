package com.example.inventorymanagementproject;

import static android.app.PendingIntent.getActivity;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.atomic.AtomicReference;

public class EmailPasswordActivity extends AppCompatActivity {

    EditText emailTxt, passTxt;
    Button loginBtn;
    TextView signUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        emailTxt = findViewById(R.id.emailTxt);
        passTxt = findViewById(R.id.passwordTxt);
        loginBtn = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.signUpLink);

        signUp.setOnClickListener(v -> SignUp());

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString().trim();
                String password = passTxt.getText().toString().trim();
                mAuth = FirebaseAuth.getInstance();
                
                if (!email.isEmpty() && !password.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(EmailPasswordActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser loggedInUser = mAuth.getCurrentUser();
                                        Intent intent = new Intent(EmailPasswordActivity.this, Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Exception exception = task.getException();
                                        Log.w(TAG, "signInWithEmail:failure", exception);
                                        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                            Toast.makeText(EmailPasswordActivity.this, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                        } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                            Toast.makeText(EmailPasswordActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SignUp(){
        Intent intent = new Intent(EmailPasswordActivity.this, SignUp.class);
        startActivity(intent);
        finish();
    }
}
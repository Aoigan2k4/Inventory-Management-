package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    EditText usernameTxt, passTxt, emailTxt;
    Button signUpBtn;
    RadioGroup roleRadioGroup;
    TextView login;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    User newUser;
    FirebaseManager mng;

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

    private void SignUpBtn(){
        String username = usernameTxt.getText().toString().trim();
        String email = emailTxt.getText().toString().trim();
        String password = passTxt.getText().toString().trim();
        mng = FirebaseManager.getInstance();
        db = mng.getDb();
        mAuth = mng.getAuth();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null) {
                                String uid = user.getUid();
                                newUser = new User(uid, username, email);
                                db.collection("Users").document(newUser.getId()).set(newUser)
                                        .addOnSuccessListener(a -> {
                                            Toast.makeText(SignUp.this, "User created!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SignUp.this, Dashboard.class);
                                            startActivity(intent);
                                            finish();
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(SignUp.this, e.toString(), Toast.LENGTH_LONG).show();
                                            login.setText(e.toString());
                                        });
                            }
                            else {
                                Toast.makeText(SignUp.this, "Failed to create user Auth", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void LogInLink(){
        Intent intent = new Intent(SignUp.this, LogIn.class);
        startActivity(intent);
        finish();
    }
}
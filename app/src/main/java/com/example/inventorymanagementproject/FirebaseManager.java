package com.example.inventorymanagementproject;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {
    private static FirebaseManager instance;
    private final FirebaseAuth mAuth;
    private final FirebaseFirestore db;

    private FirebaseManager() {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public static synchronized FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    void CreateUser(Context context, String role, String password, String username, String email) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String uid = user.getUid();
                            User newUser = new User (uid, username, email, role);

                            db.collection("Users")
                                    .document(role)
                                    .collection(role)
                                    .document(newUser.getId())
                                    .set(newUser)
                                    .addOnSuccessListener(a -> {
                                        Toast.makeText(context, "User created!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(context, Dashboard.class);
                                        intent.putExtra("role", role);
                                        ((Activity) context).startActivity(intent);
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                                    });
                        } else {
                            Toast.makeText(context, "Failed to create user Auth", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void LogInUser(Context context, String email, String password, String role){
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener( task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser loggedInUser = mAuth.getCurrentUser();
                            Intent intent = new Intent(context, Dashboard.class);
                            intent.putExtra("role", role);
                            ((Activity) context).startActivity(intent);
                        } else {
                            Exception exception = task.getException();
                            Log.w(TAG, "signInWithEmail:failure", exception);
                            if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(context, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                            } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(context, "User not found.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
        }
    }
}

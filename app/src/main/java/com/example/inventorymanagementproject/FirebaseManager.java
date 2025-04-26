package com.example.inventorymanagementproject;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
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

    void CreateUser(Context context, String role, String password, String username, String email, String adminPassword, String adminUsername, Boolean isAdmin) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            User newUser = new User();
                            newUser.setId(user.getUid());
                            newUser.setRole(role);
                            newUser.setEmail(email);
                            newUser.setUsername(username);

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                                    .Builder()
                                    .setDisplayName(username)
                                    .build();

                            user.updateProfile(profileUpdates);

                            db.collection("Users")
                                    .document(role)
                                    .collection(role)
                                    .document(newUser.getId())
                                    .set(newUser)
                                    .addOnSuccessListener(a -> {
                                        Toast.makeText(context, "User created!", Toast.LENGTH_SHORT).show();
                                        if (isAdmin) {
                                            mAuth.signOut();
                                            LogInUser(context, adminUsername, adminPassword, "Admin");
                                        }
                                        else {
                                            Intent intent = new Intent(context, Dashboard.class);
                                            ((Activity) context).startActivity(intent);
                                        }
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

    public void LogInUser(Context context, String username, String password, String role){
        var query = db.collection("Users")
                .document(role)
                .collection(role)
                .whereEqualTo("username", username)
                .get();

        query.addOnSuccessListener(querySnapshot -> {
            if (!querySnapshot.isEmpty()) {
                DocumentSnapshot userDoc = querySnapshot.getDocuments().get(0);
                String email = userDoc.getString("email");

                assert email != null;
                if (!email.isEmpty() && !password.isEmpty() && !role.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener( task -> {
                                if (task.isSuccessful()) {
                                    FirebaseUser loggedInUser = mAuth.getCurrentUser();

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                                            .Builder()
                                            .setDisplayName(username)
                                            .build();

                                    assert loggedInUser != null;
                                    loggedInUser.updateProfile(profileUpdates);

                                    Intent intent = new Intent(context, Dashboard.class);
                                    ((Activity) context).startActivity(intent);
                                } else {
                                    Exception exception = task.getException();
                                    Log.w(TAG, "signInWithEmail:failure", exception);
                                    if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(context, "Invalid email or password.", Toast.LENGTH_SHORT).show();
                                    } else if (exception instanceof FirebaseAuthInvalidUserException) {
                                        Toast.makeText(context, "User not found.", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(context, email + username + password + role, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            } else {
               Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
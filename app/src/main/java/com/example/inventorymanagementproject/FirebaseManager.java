package com.example.inventorymanagementproject;

import com.google.firebase.auth.FirebaseAuth;
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
}

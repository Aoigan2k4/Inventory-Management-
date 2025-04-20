package com.example.inventorymanagementproject.Facade;

import androidx.annotation.NonNull;

import com.example.inventorymanagementproject.Builder.Item;
import com.example.inventorymanagementproject.FirebaseManager;
import com.example.inventorymanagementproject.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserListFacade {
/**
 * Facade for User Management (Read, Update).
 */
    private final FirebaseFirestore db;
    public UserListFacade() {
    db = FirebaseManager.getInstance().getDb();
}

    public void deleteUser(@NonNull User user,
                           @NonNull OnSuccessListener<Void> onSuccessListener,
                           @NonNull OnFailureListener onFailureListener) {
        db.collection("Users")
                .document(user.getRole())
                .collection(user.getRole())
                .document(user.getId())
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}

package com.example.inventorymanagementproject.Facade;

import androidx.annotation.NonNull;

import com.example.inventorymanagementproject.Builder.Item;
import com.example.inventorymanagementproject.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Facade for Inventory Management (Create, Read, Update, Delete).
 */
public class InventoryFacade {
    private final FirebaseFirestore db;

    public InventoryFacade() {
        db = FirebaseManager.getInstance().getDb();
    }

    public void createItem(@NonNull Item item,
                           @NonNull OnSuccessListener<Void> onSuccessListener,
                           @NonNull OnFailureListener onFailureListener) {
        db.collection("Items")
                .document(item.getType())
                .collection(item.getType())
                .document(item.getId())
                .set(item)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void updateItem(@NonNull Item item,
                           @NonNull OnSuccessListener<Void> onSuccessListener,
                           @NonNull OnFailureListener onFailureListener) {
        db.collection("Items")
                .document(item.getType())
                .collection(item.getType())
                .document(item.getId())
                .set(item)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void deleteItem(@NonNull Item item,
                           @NonNull OnSuccessListener<Void> onSuccessListener,
                           @NonNull OnFailureListener onFailureListener) {
        db.collection("Items")
                .document(item.getType())
                .collection(item.getType())
                .document(item.getId())
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}
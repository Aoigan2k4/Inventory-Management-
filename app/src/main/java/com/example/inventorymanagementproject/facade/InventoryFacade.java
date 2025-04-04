package com.example.inventorymanagementproject.facade;

import androidx.annotation.NonNull;

import com.example.inventorymanagementproject.Builder.Items;
import com.example.inventorymanagementproject.FirebaseManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Facade for Inventory Management (Create, Update, Delete).
 */
public class InventoryFacade {
    private final FirebaseFirestore db;

    public InventoryFacade() {
        // Use your FirebaseManager Singleton
        db = FirebaseManager.getInstance().getDb();
    }

    /**
     * CREATE: Adds a new item document to Firestore
     */
    public void createItem(@NonNull Items item,
                           @NonNull OnSuccessListener<Void> onSuccessListener,
                           @NonNull OnFailureListener onFailureListener) {
        // Using item.getId() as document ID (ensure it's unique or auto-generate if needed)
        db.collection("Items")
                .document(item.getId())
                .set(item)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * UPDATE: Updates an existing item in Firestore
     */
    public void updateItem(@NonNull Items item,
                           @NonNull OnSuccessListener<Void> onSuccessListener,
                           @NonNull OnFailureListener onFailureListener) {
        db.collection("Items")
                .document(item.getId())
                .set(item)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    /**
     * DELETE: Removes an item from Firestore
     */
    public void deleteItem(@NonNull String itemId,
                           @NonNull OnSuccessListener<Void> onSuccessListener,
                           @NonNull OnFailureListener onFailureListener) {
        db.collection("Items")
                .document(itemId)
                .delete()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }
}

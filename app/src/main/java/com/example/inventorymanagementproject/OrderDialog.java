package com.example.inventorymanagementproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.inventorymanagementproject.Inventory.InventoryListActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class OrderDialog {
    private Dialog dialog;
    private Activity activity;
    private FirebaseManager mng;
    private FirebaseFirestore db;

    public OrderDialog(Activity activity, String itemID, String type) {
        this.activity = activity;
        this.dialog = new Dialog(activity, R.style.DialogStyle);
        this.dialog.setContentView(R.layout.order_dialog);
        Objects.requireNonNull(this.dialog.getWindow()).setLayout(1000, 1100);
        dialog.setCancelable(true);

        buildDialog(itemID, type);
    }

    private void buildDialog(String itemID, String type) {
        EditText quantityTxt = dialog.findViewById(R.id.orderQuantity);
        EditText sumTxt = dialog.findViewById(R.id.orderSum);
        Button orderButton = dialog.findViewById(R.id.orderBtn);

        mng = FirebaseManager.getInstance();
        db = mng.getDb();

        quantityTxt.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (!charSequence.toString().isEmpty()) {
                    try {
                        int quantityOrder = Integer.parseInt(charSequence.toString());
                        db.collection("Items")
                                .document(type)
                                .collection(type)
                                .whereEqualTo("id", itemID)
                                .get()
                                .addOnSuccessListener(querySnapshot -> {
                                    if (!querySnapshot.isEmpty()) {
                                        DocumentSnapshot itemDoc = querySnapshot.getDocuments().get(0);
                                        double price = itemDoc.getDouble("price");
                                        double sum = quantityOrder * price;
                                        sumTxt.setText("$" + String.format("%.2f", sum)); // Set sum with two decimal places
                                    }
                                });
                    } catch (NumberFormatException e) {
                        sumTxt.setText("$0.00");
                    }
                } else {
                    sumTxt.setText("$0.00");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        orderButton.setOnClickListener(v -> {
            String quantityInput = quantityTxt.getText().toString();
            var query = db.collection("Items")
                    .document(type)
                    .collection(type)
                    .whereEqualTo("id", itemID)
                    .get();

            query.addOnSuccessListener(querySnapshot -> {
                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot itemDoc = querySnapshot.getDocuments().get(0);
                    int quantity = itemDoc.getLong("quantity").intValue();
                    String name = itemDoc.getString("name");

                    if (quantityInput.isEmpty()) {
                        Toast.makeText(activity, "Insufficient quantity in stock!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    int quantityOrder = Integer.parseInt(quantityInput);

                    if (quantityOrder <= quantity) {
                        int newQuantity = quantity - quantityOrder;
                        itemDoc.getReference().update("quantity", newQuantity);
                        placeOrder(name, quantityOrder);
                    }
                    else{
                        Toast.makeText(activity, "Insufficient quantity in stock!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

    private void placeOrder(String itemName, int quantity) {
        Toast.makeText(activity, "Ordering " + quantity + " units of " + itemName, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(activity, InventoryListActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public void show() {
        dialog.show();
    }
}

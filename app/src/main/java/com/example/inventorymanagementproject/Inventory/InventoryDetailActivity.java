package com.example.inventorymanagementproject.Inventory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.Builder.Item;
import com.example.inventorymanagementproject.Facade.InventoryFacade;
import com.example.inventorymanagementproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class InventoryDetailActivity extends AppCompatActivity {

    private EditText editName, editBrand, editPrice, editDesc, editQuantity;
    private Button btnUpdate, btnDelete;
    private InventoryFacade inventoryFacade;
    private FirebaseFirestore db;
    private String itemId, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_detail);

        editName = findViewById(R.id.editItemName);
        editBrand = findViewById(R.id.editItemBrand);
        editPrice = findViewById(R.id.editItemPrice);
        editDesc = findViewById(R.id.editItemDesc);
        editQuantity = findViewById(R.id.editItemQuantity);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        inventoryFacade = new InventoryFacade();
        db = FirebaseFirestore.getInstance();

        itemId = getIntent().getStringExtra("itemId");
        type = getIntent().getStringExtra("type");

        if(itemId != null) {
            loadItem(itemId);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });
    }

    private void loadItem(String itemId) {
        db.collection("Items").document(type).collection(type)
                .document(itemId).get()
            .addOnSuccessListener(documentSnapshot -> {
                if(documentSnapshot.exists()) {
                    Item item = documentSnapshot.toObject(Item.class);
                    if(item != null) {
                        editName.setText(item.getName());
                        editBrand.setText(item.getBrand());
                        editPrice.setText(item.getPrice());
                        editDesc.setText(item.getDesc());
                        editQuantity.setText(item.getQuantity());
                    }
                }
            })
            .addOnFailureListener(e -> {
                Toast.makeText(InventoryDetailActivity.this, "Failed to load item", Toast.LENGTH_SHORT).show();
            });
    }

    private void updateItem() {
        Item item = new Item();
        item.setId(itemId);
        item.setName(editName.getText().toString().trim());
        item.setBrand(editBrand.getText().toString().trim());
        item.setPrice(editPrice.getText().toString().trim());
        item.setDesc(editDesc.getText().toString().trim());
        item.setQuantity(editQuantity.getText().toString().trim());
        item.setType(type);

        inventoryFacade.updateItem(item, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(InventoryDetailActivity.this, "Item updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InventoryDetailActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteItem() {
        Item item = new Item();
        item.setId(itemId);
        item.setName(editName.getText().toString().trim());
        item.setBrand(editBrand.getText().toString().trim());
        item.setPrice(editPrice.getText().toString().trim());
        item.setDesc(editDesc.getText().toString().trim());
        item.setQuantity(editQuantity.getText().toString().trim());
        item.setType(type);

        inventoryFacade.deleteItem(item, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(InventoryDetailActivity.this, "Item deleted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InventoryDetailActivity.this, "Delete failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.example.inventorymanagementproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.inventorymanagementproject.Builder.Items;
import com.example.inventorymanagementproject.facade.InventoryFacade;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class InventoryActivity extends AppCompatActivity {

    private EditText editId, editName, editBrand, editPrice, editDesc, editQuantity;
    private Button btnCreate;
    private InventoryFacade inventoryFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure this references the correct layout file which includes the create button
        setContentView(R.layout.activity_inventory);

        // Initialize input fields
        editId = findViewById(R.id.editItemId);
        editName = findViewById(R.id.editItemName);
        editBrand = findViewById(R.id.editItemBrand);
        editPrice = findViewById(R.id.editItemPrice);
        editDesc = findViewById(R.id.editItemDesc);
        editQuantity = findViewById(R.id.editItemQuantity);

        // Initialize the Create button
        btnCreate = findViewById(R.id.btnCreate);

        // Initialize your facade to interact with Firestore
        inventoryFacade = new InventoryFacade();

        // Set click listener for the Create button
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createItem();
            }
        });
    }

    private void createItem() {
        // Get text from all input fields
        String id = editId.getText().toString().trim();
        String name = editName.getText().toString().trim();
        String brand = editBrand.getText().toString().trim();
        String price = editPrice.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        String quantity = editQuantity.getText().toString().trim();

        // Optional: Validate inputs before creating the item
        if (id.isEmpty() || name.isEmpty() || brand.isEmpty() || price.isEmpty() ||
                desc.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the item using your builder/model
        Items item = new Items();
        item.setId(id);
        item.setName(name);
        item.setBrand(brand);
        item.setPrice(price);
        item.setDesc(desc);
        item.setQuantity(quantity);

        // Use your facade to create the item in Firestore
        inventoryFacade.createItem(item, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(InventoryActivity.this, "Item created successfully", Toast.LENGTH_SHORT).show();
                clearFields();
                finish(); // Optionally close the activity to return to the list
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(InventoryActivity.this, "Failed to create item: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Optional helper method to clear the input fields after creation
    private void clearFields() {
        editId.setText("");
        editName.setText("");
        editBrand.setText("");
        editPrice.setText("");
        editDesc.setText("");
        editQuantity.setText("");
    }
}

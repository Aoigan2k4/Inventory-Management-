package com.example.inventorymanagementproject.Inventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.AbstractFactory.AbstractItem;
import com.example.inventorymanagementproject.AbstractFactory.Type;
import com.example.inventorymanagementproject.Builder.Engineer;
import com.example.inventorymanagementproject.Builder.Item;
import com.example.inventorymanagementproject.Facade.InventoryFacade;
import com.example.inventorymanagementproject.R;
import com.example.inventorymanagementproject.UserList.UserListDetail;
import com.example.inventorymanagementproject.UserList.UserListView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.UUID;

public class InventoryActivity extends AppCompatActivity {

    private EditText editName, editBrand, editPrice, editDesc, editQuantity;
    private Button btnCreate, electronic, furniture, clothing;
    private InventoryFacade inventoryFacade;
    private LinearLayout buttons, edit;
    private Item newItem;
    private String itemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        editName = findViewById(R.id.editItemName);
        editBrand = findViewById(R.id.editItemBrand);
        editPrice = findViewById(R.id.editItemPrice);
        editDesc = findViewById(R.id.editItemDesc);
        editQuantity = findViewById(R.id.editItemQuantity);
        btnCreate = findViewById(R.id.btnCreate);
        electronic = findViewById(R.id.btnElectronic);
        clothing = findViewById(R.id.btnClothing);
        furniture = findViewById(R.id.btnFurniture);
        buttons = findViewById(R.id.buttons);
        edit = findViewById(R.id.editId);

        inventoryFacade = new InventoryFacade();

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createItem();
            }
        });

        electronic.setOnClickListener(this::itemChosen);
        clothing.setOnClickListener(this::itemChosen);
        furniture.setOnClickListener(this::itemChosen);
    }

    private void itemChosen(View v) {
        Button clickedButton = (Button) v;
        itemType = clickedButton.getText().toString();
        buttons.setVisibility(View.GONE);
        edit.setVisibility(View.VISIBLE);
    }

    private Item createElectronic(String id, String name, String brand, Double price, String desc, int quantity, String itemType) {
        AbstractItem electronicFactory = AbstractItem.itemEnum(Type.Electronic);
        Engineer engineer = new Engineer(electronicFactory.buildElectronic());
        engineer.BuildItem(id, name, brand, price, desc, quantity, itemType, "Available");
        return engineer.getItems();
    }

    private Item createClothing(String id, String name, String brand, Double price, String desc, int quantity, String itemType) {
        AbstractItem clothingFactory = AbstractItem.itemEnum(Type.Clothing);
        Engineer engineer = new Engineer(clothingFactory.buildClothing());
        engineer.BuildItem(id, name, brand, price, desc, quantity, itemType, "Available");
        return engineer.getItems();
    }

    private Item createFurniture(String id, String name, String brand, Double price, String desc, int quantity, String itemType) {
        AbstractItem furnitureFactory = AbstractItem.itemEnum(Type.Furniture);
        Engineer engineer = new Engineer(furnitureFactory.buildFurniture());
        engineer.BuildItem(id, name, brand, price, desc, quantity, itemType, "Available");
        return engineer.getItems();
    }

    private void createItem() {
        String id = UUID.randomUUID().toString();
        String name = editName.getText().toString().trim();
        String brand = editBrand.getText().toString().trim();
        String price = editPrice.getText().toString().trim();
        String desc = editDesc.getText().toString().trim();
        String quantity = editQuantity.getText().toString().trim();

        if (name.isEmpty() || brand.isEmpty() || price.isEmpty() ||
                desc.isEmpty() || quantity.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if(quantity.equals("0")) {
            Toast.makeText(this, "Quantity cannot be 0", Toast.LENGTH_SHORT).show();
            return;
        }

        if(price.equals("0")) {
            Toast.makeText(this, "Price cannot be 0", Toast.LENGTH_SHORT).show();
            return;
        }

        Double priceDouble = Double.parseDouble(price);
        int quantityInt = Integer.parseInt(quantity);

        switch (itemType) {
            case "Electronic":
                newItem = createElectronic(id, name, brand, priceDouble, desc, quantityInt, itemType);
                break;
            case "Clothing":
                newItem = createClothing(id, name, brand, priceDouble, desc, quantityInt, itemType);
                break;
            case "Furniture":
                newItem = createFurniture(id, name, brand, priceDouble, desc, quantityInt, itemType);
                break;
        }

        if(newItem != null) {
            Toast.makeText(this, newItem.getType(), Toast.LENGTH_SHORT).show();
            inventoryFacade.createItem(newItem, new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(InventoryActivity.this, "Item created successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                    Intent intent = new Intent(InventoryActivity.this, InventoryListActivity.class);
                    startActivity(intent);
                    finish();

                }
            }, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(InventoryActivity.this, "Failed to create item: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            Toast.makeText(this, "Failed to create item", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearFields() {
        editName.setText("");
        editBrand.setText("");
        editPrice.setText("");
        editDesc.setText("");
        editQuantity.setText("");
    }
}
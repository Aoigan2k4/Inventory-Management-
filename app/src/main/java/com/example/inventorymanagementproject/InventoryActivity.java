package com.example.inventorymanagementproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.Builder.Engineer;
import com.example.inventorymanagementproject.Builder.Items;
import com.example.inventorymanagementproject.AbstractFactory.ClothingFactory;
import com.example.inventorymanagementproject.AbstractFactory.ElectronicFactory;
import com.example.inventorymanagementproject.AbstractFactory.FurnitureFactory;
import com.example.inventorymanagementproject.facade.InventoryFacade;
import com.example.inventorymanagementproject.Users.Admin;
import com.example.inventorymanagementproject.Users.Client;
import com.example.inventorymanagementproject.Users.Staff;
import com.example.inventorymanagementproject.Users.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class InventoryActivity extends AppCompatActivity {

    private EditText editId, editName, editBrand, editPrice, editDesc, editQuantity;
    private Spinner spinnerType;
    private Button btnCreate, btnUpdate, btnDelete;
    private InventoryFacade inventoryFacade;
    private User currentUser; // You can obtain the logged-in user from intent or a user manager

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        // Initialize UI components
        editId       = findViewById(R.id.editItemId);
        editName     = findViewById(R.id.editItemName);
        editBrand    = findViewById(R.id.editItemBrand);
        editPrice    = findViewById(R.id.editItemPrice);
        editDesc     = findViewById(R.id.editItemDesc);
        editQuantity = findViewById(R.id.editItemQuantity);
        spinnerType  = findViewById(R.id.spinnerType);
        btnCreate    = findViewById(R.id.btnCreate);
        btnUpdate    = findViewById(R.id.btnUpdate);
        btnDelete    = findViewById(R.id.btnDelete);

        // Setup spinner adapter (make sure you have defined R.array.item_types_array in strings.xml)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.item_types_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        // Create the Facade instance for Firebase operations
        inventoryFacade = new InventoryFacade();

        // Retrieve the current user role from intent and create a dummy user for demonstration.
        // In your actual app, pass the logged-in user or use a user manager.
        String userRole = getIntent().getStringExtra("role");
        currentUser = createUserByRole(userRole);
        configureUIByRole(currentUser);

        // Set button click events
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createItem();
            }
        });
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

    /**
     * For demonstration: creates a User instance based on the role string.
     */
    private User createUserByRole(String role) {
        String dummyId = "12345";
        String dummyName = "John Doe";
        String dummyEmail = "johndoe@example.com";

        if ("Admin".equalsIgnoreCase(role)) {
            return new Admin(dummyId, dummyName, dummyEmail);
        } else if ("Staff".equalsIgnoreCase(role)) {
            return new Staff(dummyId, dummyName, dummyEmail);
        } else {
            return new Client(dummyId, dummyName, dummyEmail);
        }
    }

    /**
     * Adjusts the UI based on the user's role.
     * - Admin: can create, update, and delete items.
     * - Staff: can create and update items (delete hidden).
     * - Client: cannot create, update, or delete items.
     */
    private void configureUIByRole(User user) {
        if (user instanceof Admin) {
            btnCreate.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        } else if (user instanceof Staff) {
            btnCreate.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        } else if (user instanceof Client) {
            btnCreate.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }
    }

    /**
     * Creates an item using the Facade.
     */
    private void createItem() {
        Items item = gatherItemFromFields();

        // Use the AbstractFactory + Builder pattern to build the item based on type
        String type = spinnerType.getSelectedItem().toString().toLowerCase();
        Engineer engineer = null;

        switch (type) {
            case "clothing":
                engineer = new Engineer(new ClothingFactory().buildClothing());
                break;
            case "electronic":
                engineer = new Engineer(new ElectronicFactory().buildElectronic());
                break;
            case "furniture":
                engineer = new Engineer(new FurnitureFactory().buildFurniture());
                break;
        }

        if (engineer != null) {
            engineer.BuildItem(item);
        }

        // Use the Facade to create the item in Firestore
        inventoryFacade.createItem(
                item,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InventoryActivity.this, "Item created!", Toast.LENGTH_SHORT).show();
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(InventoryActivity.this, "Create failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /**
     * Updates an item using the Facade.
     */
    private void updateItem() {
        Items item = gatherItemFromFields();
        inventoryFacade.updateItem(
                item,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InventoryActivity.this, "Item updated!", Toast.LENGTH_SHORT).show();
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(InventoryActivity.this, "Update failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /**
     * Deletes an item using the Facade.
     */
    private void deleteItem() {
        String itemId = editId.getText().toString().trim();
        if (itemId.isEmpty()) {
            Toast.makeText(this, "Please enter the Item ID to delete", Toast.LENGTH_SHORT).show();
            return;
        }
        inventoryFacade.deleteItem(
                itemId,
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(InventoryActivity.this, "Item deleted!", Toast.LENGTH_SHORT).show();
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(InventoryActivity.this, "Delete failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    /**
     * Collects item information from input fields and returns an Items object.
     */
    private Items gatherItemFromFields() {
        Items item = new Items();
        item.setId(editId.getText().toString().trim());
        item.setName(editName.getText().toString().trim());
        item.setBrand(editBrand.getText().toString().trim());
        item.setPrice(editPrice.getText().toString().trim());
        item.setDesc(editDesc.getText().toString().trim());
        item.setQuantity(editQuantity.getText().toString().trim());
        return item;
    }
}

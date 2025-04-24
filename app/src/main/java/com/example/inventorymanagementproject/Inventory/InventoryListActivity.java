package com.example.inventorymanagementproject.Inventory;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagementproject.Builder.Item;
import com.example.inventorymanagementproject.Dashboard;
import com.example.inventorymanagementproject.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InventoryListActivity extends AppCompatActivity implements InventoryAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private InventoryAdapter adapter;
    private List<Item> itemList;
    private FirebaseFirestore db;
    private Spinner spinner;
    private String sortType;
    private EditText search, brandTxt, priceTxt, quantityTxt;
    private Button btnSearch;
    private String name, brand, price, quantity;
    private View touchZone;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        spinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new InventoryAdapter(itemList, this);
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        search = findViewById(R.id.ItemName);
        brandTxt = findViewById(R.id.Brand);
        priceTxt = findViewById(R.id.Price);
        quantityTxt = findViewById(R.id.Quantity);
        btnSearch = findViewById(R.id.searchBtn);
        touchZone = findViewById(R.id.touchZone);

        name = search.getText().toString();
        brand = brandTxt.getText().toString();
        price = priceTxt.getText().toString().trim();
        quantity = quantityTxt.getText().toString().trim();

        touchZone.setOnClickListener(v-> {
            Intent intent = new Intent(InventoryListActivity.this, Dashboard.class);
            startActivity(intent);
            finish();
        });


        List<String> itemTypes = Arrays.asList("All", "Electronic", "Clothing", "Furniture");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemTypes);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortType = parent.getItemAtPosition(position).toString();
                loadAllItems(sortType, name, brand, price, quantity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortType = parent.getItemAtPosition(0).toString();
                loadAllItems(sortType, name, brand, price, quantity);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = search.getText().toString();
                brand = brandTxt.getText().toString();
                price = priceTxt.getText().toString().trim();
                quantity = quantityTxt.getText().toString().trim();
                loadAllItems(sortType, name, brand, price, quantity);
            }
        });
    }

    private void loadAllItems(String sort, @Nullable String name, @Nullable String brand, @Nullable String price, @Nullable String quantity) {
        itemList.clear();
        Double priceDouble = -1.0;
        int quantityInt = -1;

        if(price != null && !price.isEmpty()) {
            priceDouble = Double.parseDouble(price);
        }

        if(quantity != null && !quantity.isEmpty()) {
            quantityInt = Integer.parseInt(quantity);
        }

        List<String> itemTypes = Arrays.asList("Electronic", "Clothing", "Furniture");
        if (Objects.equals(sort, "All")) {
            for (String type : itemTypes) {
               sortItems(type, name, brand, priceDouble, quantityInt);
            }
        }else {
           sortItems(sort, name, brand, priceDouble, quantityInt);
        }
    }

    private void sortItems(String sortType, @Nullable String name, @Nullable String brand, @Nullable Double price, @Nullable Integer quantity) {
        com.google.firebase.firestore.Query query = db
                .collection("Items")
                .document(sortType)
                .collection(sortType);

        if(name != null && !name.isEmpty()) {
            query = query.whereEqualTo("name", name);
        }

        if(brand != null && !brand.isEmpty()) {
            query = query.whereEqualTo("brand", brand);
        }

        if(price != null && price != -1) {
            query = query.whereLessThanOrEqualTo("price", price);
            query = query.orderBy("price");
        }

        if(quantity != null && quantity != -1) {
            query = query.whereLessThanOrEqualTo("quantity", quantity);
            query = query.orderBy("quantity");
        }

        query.get()
            .addOnSuccessListener(querySnapshot -> {
                if (querySnapshot.isEmpty()) {
                    return;
                }

                for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                    Item item = doc.toObject(Item.class);
                    if (item != null) {
                        itemList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            })
            .addOnFailureListener(e -> {
                Toast.makeText(this, "Failed to fetch data.", Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {}

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(InventoryListActivity.this, InventoryDetailActivity.class);
        intent.putExtra("itemId", item.getId());
        intent.putExtra("type", item.getType());
        startActivity(intent);
    }
}

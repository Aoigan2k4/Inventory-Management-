package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagementproject.Builder.Item;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        spinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new InventoryAdapter(itemList, this);
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();

        adapter.notifyDataSetChanged();

        List<String> itemTypes = Arrays.asList("All", "Electronic", "Clothing", "Furniture");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemTypes);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortType = parent.getItemAtPosition(position).toString();
                loadAllItems(sortType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortType = parent.getItemAtPosition(0).toString();
                loadAllItems(sortType);
            }
        });
    }


    private void loadAllItems(String sort) {
        itemList.clear();
        List<String> itemTypes = Arrays.asList("Electronic", "Clothing", "Furniture");
        if (Objects.equals(sort, "All")) {
            for (String type : itemTypes) {
                db.collection("Items")
                        .document(type)
                        .collection(type)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                List<Item> items = queryDocumentSnapshots.toObjects(Item.class);
                                itemList.addAll(items);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(this, "Failed to load items from " + type + ": " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        }else {
           sortItems(sort);
        }
    }

    private void sortItems(String sortType) {
        db.collection("Items").document(sortType).collection(sortType)
            .get()
            .addOnSuccessListener(querySnapshot -> {
                for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                    Item item = doc.toObject(Item.class);
                    if (item != null) {
                        itemList.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }
            })
            .addOnFailureListener(e -> {
                Toast.makeText(InventoryListActivity.this, "Error loading items: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

    }

    @Override
    public void onItemClick(Item item) {
        Intent intent = new Intent(InventoryListActivity.this, InventoryDetailActivity.class);
        intent.putExtra("itemId", item.getId());
        intent.putExtra("type", item.getType());
        startActivity(intent);
    }
}

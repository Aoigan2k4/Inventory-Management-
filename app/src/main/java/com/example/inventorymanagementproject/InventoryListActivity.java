package com.example.inventorymanagementproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inventorymanagementproject.Builder.Items;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;

public class InventoryListActivity extends AppCompatActivity implements InventoryAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private InventoryAdapter adapter;
    private List<Items> itemList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        adapter = new InventoryAdapter(itemList, this);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        loadItems();
    }

    private void loadItems() {
        CollectionReference itemsRef = db.collection("Items");
        // Listen for real-time updates
        itemsRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(InventoryListActivity.this, "Error loading items: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                itemList.clear();
                if (value != null) {
                    itemList.addAll(value.toObjects(Items.class));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(Items item) {
        // When an item is clicked, open InventoryDetailActivity for editing or deletion
        Intent intent = new Intent(InventoryListActivity.this, InventoryDetailActivity.class);
        intent.putExtra("itemId", item.getId());
        startActivity(intent);
    }
}

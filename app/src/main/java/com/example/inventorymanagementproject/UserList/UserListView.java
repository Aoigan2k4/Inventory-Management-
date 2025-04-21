package com.example.inventorymanagementproject.UserList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagementproject.Inventory.InventoryDetailActivity;
import com.example.inventorymanagementproject.Inventory.InventoryListActivity;
import com.example.inventorymanagementproject.R;
import com.example.inventorymanagementproject.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserListView extends AppCompatActivity implements UserListAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private UserListAdapter adapter;
    private List<User> userList;
    private FirebaseFirestore db;
    private Spinner spinner;
    private String sortRole;
    private EditText search;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_view);

        search = findViewById(R.id.Username);
        spinner = findViewById(R.id.spinner);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        adapter = new UserListAdapter(userList, this);
        btnSearch = findViewById(R.id.searchBtn);

        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        String name = search.getText().toString();
        
        List<String> userSort = Arrays.asList("All", "Admin", "Staff", "Client");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, userSort);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortRole = parent.getItemAtPosition(position).toString();
                loadAllUsers(sortRole, name);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortRole = parent.getItemAtPosition(0).toString();
                loadAllUsers(sortRole, name);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = search.getText().toString();
                loadAllUsers(sortRole, name);
            }
        });
    }

    private void loadAllUsers(String sort, @Nullable String name) {
        userList.clear();
        List<String> userSort = Arrays.asList("Admin", "Staff", "Client");
        if (Objects.equals(sort, "All")) {
            for (String type : userSort) {
                sortUsersByName(type, name);
            }
        }else {
            sortUsersByName(sort, name);
        }
    }

//    private void sortUsers(String sortRole) {
//        db.collection("Users").document(sortRole).collection(sortRole)
//                .get()
//                .addOnSuccessListener(querySnapshot -> {
//                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
//                        User user = doc.toObject(User.class);
//                        if (user != null) {
//                            userList.add(user);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }
//                })
//                .addOnFailureListener(e -> {
//                    Toast.makeText((UserListView.this), "Error loading users: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                });
//    }

    private void sortUsersByName(String sortRole, @Nullable String name) {
        com.google.firebase.firestore.Query query = db.collection("Users")
                .document(sortRole)
                .collection(sortRole);

        if(name != null && !name.isEmpty()) {
            query = query.whereEqualTo("username", name);
        }

        query.get()
        .addOnSuccessListener(querySnapshot -> {
            if (querySnapshot.isEmpty()) {
                userList.clear();
                adapter.notifyDataSetChanged();
                return;
            }

            for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                User user = doc.toObject(User.class);
                if (user != null) {
                    userList.add(user);
                }
            }
            adapter.notifyDataSetChanged();
        })
        .addOnFailureListener(e -> {
            Toast.makeText((UserListView.this), "Error loading users: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {}

    @Override
    public void onItemClick(User user) {
        Intent intent = new Intent(UserListView.this, UserListDetail.class);
        intent.putExtra("userId", user.getId());
        intent.putExtra("role", user.getRole());
        startActivity(intent);
    }
}
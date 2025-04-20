package com.example.inventorymanagementproject.UserList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagementproject.Builder.Item;
import com.example.inventorymanagementproject.Inventory.InventoryAdapter;
import com.example.inventorymanagementproject.R;
import com.example.inventorymanagementproject.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    public interface OnItemClickListener {
        void onItemSelected(AdapterView<?> parent, View v, int position, long id);
        void onItemClick(User user);
    }

    private List<User> userList;
    private UserListAdapter.OnItemClickListener listener;

    public UserListAdapter(List<User> userList, UserListAdapter.OnItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserListAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_custom, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder holder, int position) {
        User user = userList.get(position);
        if(user != null) {
            holder.bind(user, listener);
        }
        else {
            Log.e("UserListAdapter", "User is null at position: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView Username, Email, Role;

        public UserViewHolder(@NonNull View userView) {
            super(userView);
            Username = itemView.findViewById(R.id.username);
            Email = itemView.findViewById(R.id.email);
            Role = itemView.findViewById(R.id.role);
        }

        public void bind(User user, UserListAdapter.OnItemClickListener listener) {
            Username.setText(user.getUsername());
            Email.setText("Email: " + user.getEmail());
            Role.setText("Role: " + user.getRole());
            itemView.setOnClickListener(v -> listener.onItemClick(user));
        }
    }
}

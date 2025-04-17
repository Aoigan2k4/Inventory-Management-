package com.example.inventorymanagementproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inventorymanagementproject.Builder.Item;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemSelected(AdapterView<?> parent, View v, int position, long id);
        void onItemClick(Item item);
    }

    private List<Item> itemList;
    private OnItemClickListener listener;

    public InventoryAdapter(List<Item> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Brand, Price, Quantity;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.name);
            Brand = itemView.findViewById(R.id.brand);
            Price = itemView.findViewById(R.id.price);
            Quantity = itemView.findViewById(R.id.quantity);
        }

        public void bind(Item item, OnItemClickListener listener) {
            Name.setText(item.getName());
            Brand.setText("Brand: " + item.getBrand());
            Price.setText("Price: $" + item.getPrice());
            Quantity.setText("Quantity in Stock: " + item.getQuantity());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}
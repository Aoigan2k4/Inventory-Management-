package com.example.inventorymanagementproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.inventorymanagementproject.Builder.Items;
import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ItemViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Items item);
    }

    private List<Items> itemList;
    private OnItemClickListener listener;

    public InventoryAdapter(List<Items> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_inventory, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Items item = itemList.get(position);
        holder.bind(item, listener);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvBrand, tvPrice, tvQuantity;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvBrand = itemView.findViewById(R.id.tvItemBrand);
            tvPrice = itemView.findViewById(R.id.tvItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvItemQuantity);
        }

        public void bind(Items item, OnItemClickListener listener) {
            tvName.setText(item.getName());
            tvBrand.setText(item.getBrand());
            tvPrice.setText(item.getPrice());
            tvQuantity.setText(item.getQuantity());
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }
}

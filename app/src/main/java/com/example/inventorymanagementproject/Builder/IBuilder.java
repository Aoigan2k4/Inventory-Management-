package com.example.inventorymanagementproject.Builder;

public interface IBuilder {
    void SetID(String id);
    void SetName(String name);
    void SetBrand(String brand);
    void SetPrice(Double price);
    void SetDesc(String desc);
    void SetQuantity(int quantity);
    void SetType(String type);
    Item getItem();
}

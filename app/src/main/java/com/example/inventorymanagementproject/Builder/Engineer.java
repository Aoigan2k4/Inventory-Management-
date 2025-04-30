package com.example.inventorymanagementproject.Builder;

public class Engineer {
    IBuilder iBuilder;

    public Engineer(IBuilder iBuilder){
        this.iBuilder = iBuilder;
    }


    public void BuildItem(String id, String name, String brand, Double price, String desc, int quantity, String itemType, String status){
        this.iBuilder.SetID(id);
        this.iBuilder.SetName(name);
        this.iBuilder.SetBrand(brand);
        this.iBuilder.SetPrice(price);
        this.iBuilder.SetDesc(desc);
        this.iBuilder.SetQuantity(quantity);
        this.iBuilder.SetType(itemType);
        this.iBuilder.SetStatus(status);
    }

    public Item getItems() {
        return iBuilder.getItem();
    }
}

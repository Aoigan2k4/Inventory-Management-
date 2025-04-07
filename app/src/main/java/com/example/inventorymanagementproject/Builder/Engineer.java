package com.example.inventorymanagementproject.Builder;

public class Engineer {
    IBuilder iBuilder;

    public Engineer(IBuilder iBuilder){
        this.iBuilder = iBuilder;
    }

    //ID will be auto-generated
    public void BuildItem(String id, String name, String brand, String price, String desc, String quantity, String itemType){
        this.iBuilder.SetID(id);
        this.iBuilder.SetName(name);
        this.iBuilder.SetBrand(brand);
        this.iBuilder.SetPrice(price);
        this.iBuilder.SetDesc(desc);
        this.iBuilder.SetQuantity(quantity);
        this.iBuilder.SetType(itemType);
    }

    public Item getItems() {
        return iBuilder.getItem();
    }
}

package com.example.inventorymanagementproject.Builder;

public class Engineer {
    IBuilder iBuilder;

    public Engineer(IBuilder iBuilder){
        this.iBuilder = iBuilder;
    }

    //ID will be auto-generated
    public void BuildItem(Items item){
        this.iBuilder.SetID(item.getId());
        this.iBuilder.SetName(item.getName());
        this.iBuilder.SetBrand(item.getBrand());
        this.iBuilder.SetPrice(item.getPrice());
        this.iBuilder.SetDesc(item.getDesc());
        this.iBuilder.SetQuantity(item.getQuantity());
    }
}

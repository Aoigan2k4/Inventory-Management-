package com.example.inventorymanagementproject.Builder;

public class FurnitureBuilder implements IBuilder{
    Items item;

    public FurnitureBuilder(){
        this.item = new Items();
    }

    @Override
    public void SetID(String id) {
        this.item.setId(id);
    }

    @Override
    public void SetName(String name) {
        this.item.setName(name);
    }

    @Override
    public void SetBrand(String brand) {
        this.item.setBrand(brand);
    }

    @Override
    public void SetPrice(String price) {
        this.item.setPrice(price);
    }

    @Override
    public void SetDesc(String desc) {
        this.item.setDesc(desc);
    }

    @Override
    public void SetQuantity(String quantity) {
        this.item.setQuantity(quantity);
    }

}

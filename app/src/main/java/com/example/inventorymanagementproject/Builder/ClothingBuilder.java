package com.example.inventorymanagementproject.Builder;

public class ClothingBuilder implements IBuilder{

    Item item;

    public ClothingBuilder(){
        this.item = new Item();
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
    public void SetPrice(Double price) {
        this.item.setPrice(price);
    }

    @Override
    public void SetDesc(String desc) {
        this.item.setDesc(desc);
    }

    @Override
    public void SetQuantity(int quantity) {
        this.item.setQuantity(quantity);
    }

    @Override
    public void SetType(String type) {
        this.item.setType(type);
    }

    @Override
    public Item getItem() {
        return item;
    }
}

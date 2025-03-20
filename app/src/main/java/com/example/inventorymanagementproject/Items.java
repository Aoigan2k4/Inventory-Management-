package com.example.inventorymanagementproject;

public class Items {
    private String id;
    private String type;
    private String desc;
    private String brand;

    public Items(String id, String type, String desc, String brand) {
        this.id = id;
        this.type = type;
        this.desc = desc;
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}

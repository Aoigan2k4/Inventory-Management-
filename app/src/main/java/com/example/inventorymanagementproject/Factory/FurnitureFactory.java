package com.example.inventorymanagementproject.Factory;
import com.example.inventorymanagementproject.Builder.FurnitureBuilder;
import com.example.inventorymanagementproject.Builder.IBuilder;

public class FurnitureFactory extends AbstractItem{
    @Override
    public IBuilder buildClothing() {
        return null;
    }

    @Override
    public IBuilder buildElectronic() {
        return null;
    }

    @Override
    public IBuilder buildFurniture() {
        return new FurnitureBuilder();
    }
}

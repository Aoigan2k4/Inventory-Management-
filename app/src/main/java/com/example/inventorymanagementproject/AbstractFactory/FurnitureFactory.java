package com.example.inventorymanagementproject.AbstractFactory;
import com.example.inventorymanagementproject.Builder.FurnitureBuilder;
import com.example.inventorymanagementproject.Builder.IBuilder;

public class FurnitureFactory extends AbstractFactory{
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

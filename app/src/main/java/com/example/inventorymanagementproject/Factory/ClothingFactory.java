package com.example.inventorymanagementproject.Factory;

import com.example.inventorymanagementproject.Builder.ClothingBuilder;
import com.example.inventorymanagementproject.Builder.IBuilder;

public class ClothingFactory extends AbstractFactory {

    @Override
    public IBuilder buildClothing(){
        return new ClothingBuilder();
    }

    @Override
    public IBuilder buildElectronic() {
        return null;
    }

    @Override
    public IBuilder buildFurniture() {
        return null;
    }
}

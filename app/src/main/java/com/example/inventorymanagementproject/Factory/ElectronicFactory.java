package com.example.inventorymanagementproject.Factory;

import com.example.inventorymanagementproject.Builder.ElectronicBuilder;
import com.example.inventorymanagementproject.Builder.IBuilder;

public class ElectronicFactory extends AbstractItem {

    @Override
    public IBuilder buildClothing() {
        return null;
    }

    @Override
    public IBuilder buildElectronic() {
        return new ElectronicBuilder();
    }

    @Override
    public IBuilder buildFurniture() {
        return null;
    }
}

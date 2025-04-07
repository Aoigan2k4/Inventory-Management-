package com.example.inventorymanagementproject.AbstractFactory;;
import com.example.inventorymanagementproject.Builder.*;

public class ItemFactory extends AbstractItem {

    @Override
    public IBuilder buildElectronic() {
        return new ElectronicBuilder();
    }

    @Override
    public IBuilder buildClothing() {
        return new ClothingBuilder();
    }

    @Override
    public IBuilder buildFurniture() {
        return new FurnitureBuilder();
    }
}

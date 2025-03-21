package com.example.inventorymanagementproject.Factory;

import com.example.inventorymanagementproject.Builder.IBuilder;

public abstract class AbstractItem {

    public abstract IBuilder buildClothing();
    public abstract IBuilder buildElectronic();
    public abstract IBuilder buildFurniture();
}

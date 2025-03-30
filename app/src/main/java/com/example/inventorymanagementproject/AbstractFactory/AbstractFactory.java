package com.example.inventorymanagementproject.AbstractFactory;

import com.example.inventorymanagementproject.Builder.IBuilder;

public abstract class AbstractFactory {

    public abstract IBuilder buildClothing();
    public abstract IBuilder buildElectronic();
    public abstract IBuilder buildFurniture();
}


package com.example.inventorymanagementproject.AbstractFactory;

import com.example.inventorymanagementproject.Builder.IBuilder;

public abstract class AbstractItem {
    static AbstractItem item = new ItemFactory();

    public static AbstractItem itemEnum (Type type){
        AbstractItem itemEnum = null;
        switch (type){
            case Electronic:
                itemEnum = item;
                break;
            case Clothing:
                itemEnum = item;
                break;
            case Furniture:
                itemEnum = item;
                break;
        }
        return itemEnum;
    }

    public abstract IBuilder buildClothing();
    public abstract IBuilder buildElectronic();
    public abstract IBuilder buildFurniture();
}


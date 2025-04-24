package com.example.inventorymanagementproject.COR;

import android.content.Context;

import com.example.inventorymanagementproject.FirebaseManager;

public abstract class ValidationHandler {
    protected ValidationHandler nextHandler;

    public void setNext(ValidationHandler next) {
        this.nextHandler = next;
    }

    public abstract void handle(Context context, ValidationManager request, FirebaseManager mng, ValidationInterface results);
}

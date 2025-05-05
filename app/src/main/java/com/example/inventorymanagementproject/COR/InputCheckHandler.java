package com.example.inventorymanagementproject.COR;

import android.content.Context;

import com.example.inventorymanagementproject.FirebaseManager;

public class InputCheckHandler extends ValidationHandler {

    @Override
    public void handle(Context context, ValidationManager request, FirebaseManager mng, ValidationInterface results) {
        if (request.username.isEmpty() || request.password.isEmpty() || request.role.isEmpty()) {
            results.onFailure("No fields can be empty.");
        } else if (nextHandler != null) {
            nextHandler.handle(context,request, mng, results);
        }
    }
}

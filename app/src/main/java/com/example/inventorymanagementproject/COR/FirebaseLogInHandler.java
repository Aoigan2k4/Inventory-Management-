package com.example.inventorymanagementproject.COR;

import android.content.Context;
import com.example.inventorymanagementproject.FirebaseManager;

public class FirebaseLogInHandler extends ValidationHandler {

    @Override
    public void handle(Context context, ValidationManager request, FirebaseManager mng, ValidationInterface callback) {
        mng.LogInUser(context, request.username, request.password, request.role);
    }
}

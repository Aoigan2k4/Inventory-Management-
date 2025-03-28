package com.example.inventorymanagementproject.chain;

import android.text.TextUtils;

public class EmptyFieldValidator extends InputHandler {
    @Override
    public boolean handleRequest(String input) {
        if (TextUtils.isEmpty(input)) {
            return false; // validation fails
        }
        return super.handleRequest(input); // pass to next if any
    }
}

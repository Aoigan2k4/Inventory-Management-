package com.example.inventorymanagementproject.TemplateMethod;

import android.app.Activity;
import android.view.View;

import com.example.inventorymanagementproject.R;

public class AdminAuthorization extends RoleAuthorization{
    @Override
    protected void SpecificRoles(String role, Activity activity) {
        if (role.equals("Admin")) {
            activity.findViewById(R.id.AddItems).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.AddUser).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.ViewUsers).setVisibility(View.VISIBLE);
        }
    }
}

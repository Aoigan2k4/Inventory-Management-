package com.example.inventorymanagementproject.TemplateMethod;

import android.app.Activity;
import android.view.View;

import com.example.inventorymanagementproject.R;

public class StaffAuthorization extends RoleAuthorization{
    @Override
    protected void SpecificRoles(String role, Activity activity) {
        if (role.equals("Staff")) {
            activity.findViewById(R.id.AddItems).setVisibility(View.VISIBLE);
            activity.findViewById(R.id.AddUser).setVisibility(View.GONE);
            activity.findViewById(R.id.ViewUsers).setVisibility(View.GONE);
        }
    }
}

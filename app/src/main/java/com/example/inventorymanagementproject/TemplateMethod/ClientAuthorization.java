package com.example.inventorymanagementproject.TemplateMethod;

import android.app.Activity;
import android.view.View;

import com.example.inventorymanagementproject.R;

public class ClientAuthorization extends RoleAuthorization {
    @Override
    protected void SpecificRoles(String role, Activity activity) {
        activity.findViewById(R.id.AddItems).setVisibility(View.GONE);
        activity.findViewById(R.id.AddUser).setVisibility(View.GONE);
        activity.findViewById(R.id.ViewUsers).setVisibility(View.GONE);
    }
}

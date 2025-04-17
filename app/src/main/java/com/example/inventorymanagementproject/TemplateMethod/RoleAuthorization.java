package com.example.inventorymanagementproject.TemplateMethod;

import android.app.Activity;
import android.view.View;

import com.example.inventorymanagementproject.R;

public abstract class RoleAuthorization {

    public final void ConfigureAuth(String role, Activity activity) {
        CommonRoles(activity);
        SpecificRoles(role, activity);
    }

    private void CommonRoles(Activity activity) {
        activity.findViewById(R.id.ViewInventory).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.LogOut).setVisibility(View.VISIBLE);
    }

    protected abstract void SpecificRoles(String role, Activity activity);
}

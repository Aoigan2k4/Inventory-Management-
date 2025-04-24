package com.example.inventorymanagementproject.TemplateMethod;

import android.app.Activity;
import android.view.View;

import com.example.inventorymanagementproject.R;

public class AdminAuthorization extends RoleAuthorization{
    @Override
    protected void SpecificRoles(String role, Activity activity) {
        if (role.equals("Admin")) {
            View addItems = activity.findViewById(R.id.AddItems);
            if(addItems != null) addItems.setVisibility(View.VISIBLE);

            View addUser = activity.findViewById(R.id.AddUser);
            if(addUser != null) addUser.setVisibility(View.VISIBLE);

            View viewUsers = activity.findViewById(R.id.ViewUsers);
            if(viewUsers != null) viewUsers.setVisibility(View.VISIBLE);

            View updateBtn = activity.findViewById(R.id.btnUpdate);
            if(updateBtn != null) updateBtn.setVisibility(View.VISIBLE);

            View deleteBtn = activity.findViewById(R.id.btnDelete);
            if(deleteBtn != null) deleteBtn.setVisibility(View.VISIBLE);

            View orderBtn = activity.findViewById(R.id.btnOrder);
            if(orderBtn != null) orderBtn.setVisibility(View.GONE);
        }
    }
}

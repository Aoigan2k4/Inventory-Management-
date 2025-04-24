package com.example.inventorymanagementproject.TemplateMethod;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import com.example.inventorymanagementproject.R;

public class ClientAuthorization extends RoleAuthorization {
    @Override
    protected void SpecificRoles(String role, Activity activity) {
        View addItems = activity.findViewById(R.id.AddItems);
        if(addItems != null) addItems.setVisibility(View.GONE);

        View addUser = activity.findViewById(R.id.AddUser);
        if(addUser != null) addUser.setVisibility(View.GONE);

        View viewUsers = activity.findViewById(R.id.ViewUsers);
        if(viewUsers != null) viewUsers.setVisibility(View.GONE);

        View updateBtn = activity.findViewById(R.id.btnUpdate);
        if(updateBtn != null) updateBtn.setVisibility(View.GONE);

        View deleteBtn = activity.findViewById(R.id.btnDelete);
        if(deleteBtn != null) deleteBtn.setVisibility(View.GONE);

        View orderBtn = activity.findViewById(R.id.btnOrder);
        if(orderBtn != null) orderBtn.setVisibility(View.VISIBLE);

        EditText editName = activity.findViewById(R.id.editItemName);
        EditText editBrand = activity.findViewById(R.id.editItemBrand);
        EditText editPrice = activity.findViewById(R.id.editItemPrice);
        EditText editDesc = activity.findViewById(R.id.editItemDesc);
        EditText editQuantity = activity.findViewById(R.id.editItemQuantity);

        if (editName != null) {
            editName.setFocusable(false);
            editName.setClickable(false);
            editName.setCursorVisible(false);
            editName.setLongClickable(false);
        }

        if (editBrand != null) {
            editBrand.setFocusable(false);
            editBrand.setClickable(false);
            editBrand.setCursorVisible(false);
            editBrand.setLongClickable(false);
        }

        if (editPrice != null) {
            editPrice.setFocusable(false);
            editPrice.setClickable(false);
            editPrice.setCursorVisible(false);
            editPrice.setLongClickable(false);
        }

        if (editDesc != null) {
            editDesc.setFocusable(false);
            editDesc.setClickable(false);
            editDesc.setCursorVisible(false);
            editDesc.setLongClickable(false);
        }

        if (editQuantity != null) {
            editQuantity.setFocusable(false);
            editQuantity.setClickable(false);
            editQuantity.setCursorVisible(false);
            editQuantity.setLongClickable(false);
        }
    }
}

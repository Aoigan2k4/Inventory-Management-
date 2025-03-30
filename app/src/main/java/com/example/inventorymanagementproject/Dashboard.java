package com.example.inventorymanagementproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.inventorymanagementproject.Builder.Engineer;
import com.example.inventorymanagementproject.Builder.Items;
import com.example.inventorymanagementproject.AbstractFactory.ClothingFactory;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Button { //Clothing
            Items item = new Items();
            ClothingFactory clothingFactory = new ClothingFactory();
            item.setQuantity("10");

            Engineer engineer = new Engineer(clothingFactory.buildClothing());
            engineer.BuildItem(item);

        //}

        // Button Submit {
        // Saved to Firebase
        // }
    }
}
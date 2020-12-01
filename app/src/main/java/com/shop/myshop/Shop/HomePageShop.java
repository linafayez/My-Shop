package com.shop.myshop.Shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shop.myshop.R;

public class HomePageShop extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_shop);
        BottomNavigationView bottomNav = findViewById(R.id.bottomBar);
        NavController nav = Navigation.findNavController(this,R.id.nav_host);
        NavigationUI.setupWithNavController(bottomNav , nav);
    }
}
package com.shop.myshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.shop.myshop.Shop.HomePageShop;

public class StartPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
       mAuth = FirebaseAuth.getInstance();
       sharedPreference = new SharedPreference(getApplicationContext());
     user();
    }
    public void user(){
        if(sharedPreference.getUser() != null){
            if (sharedPreference.getUser().getType().equals("Admin")) {
                Intent done = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(done);
            }
            if ("User".equals(sharedPreference.getUser().getType())) {
                Intent done = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(done);
                //finish();
            }if ("Shop owner".equals(sharedPreference.getUser().getType())){
                Intent done = new Intent(getApplicationContext(), HomePageShop.class);
                startActivity(done);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        user();
    }
}
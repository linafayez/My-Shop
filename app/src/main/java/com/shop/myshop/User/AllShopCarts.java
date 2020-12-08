package com.shop.myshop.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shop.myshop.Models.shopModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;

import java.util.ArrayList;

public class AllShopCarts extends Fragment {
    RecyclerView cart;
    ShopAdapter adapter;
    ArrayList<shopModel> data;
    SharedPreference sharedPreference;
    TextView text;
    public AllShopCarts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_shop_carts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cart = view.findViewById(R.id.all);
        text =view.findViewById(R.id.textView27);
        sharedPreference = new SharedPreference(getContext());
        data = new ArrayList<>();
        if(sharedPreference.getAllShop()!= null){
            data = sharedPreference.getAllShop();
        }
        if(data.size()== 0){
            text.setVisibility(View.VISIBLE);
        }

        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new ShopAdapter(data);
        cart.setLayoutManager(manager);
        cart.setHasFixedSize(false);
        cart.setAdapter(adapter);

    }
}
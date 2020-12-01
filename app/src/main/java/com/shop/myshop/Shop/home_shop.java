package com.shop.myshop.Shop;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shop.myshop.Models.shopModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;

public class home_shop extends Fragment {
    Button newProduct,newCategory, editCategory , editProduct , addPromoCode,Ads , deals;
   shopModel shopModel;
   SharedPreference sharedPreference ;
    public home_shop() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreference = new SharedPreference(getContext());
        shopModel = sharedPreference.getShop();
        deals = view.findViewById(R.id.deals);
        newProduct = view.findViewById(R.id.newProduct);
        addPromoCode = view.findViewById(R.id.promoCode);
        newCategory = view.findViewById(R.id.newCategory);
        newCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Navigation.findNavController(getView()).navigate(home_shopDirections.actionHomeShopToAddCategories2(null));

            }
        });
        editProduct= view.findViewById(R.id.editProduct);
     //   newPromoCode = view.findViewById(R.id.promoCode);
        newCategory= view.findViewById(R.id.newCategory);
        editCategory = view.findViewById(R.id.editCategory);
        Ads = view.findViewById(R.id.newAds);

        newProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCategory("newProduct");
            }
        });

        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCategory("editProduct");
            }
        });
        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToCategory("editCategory");
            }
        });
        Ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCategory("Ads");
            }
        });
        deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   bundle.putString("type","deals");
                goToCategory("deals");
            }
        });
        addPromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Navigation.findNavController(getView()).navigate(R.id.action_home_shop_to_addPromoCode);
            }
        });
    }

    private void goToCategory(String type) {
     Navigation.findNavController(getView()).navigate(home_shopDirections.actionHomeShopToCategory(type));
    }
    }

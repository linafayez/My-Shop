package com.shop.myshop.User;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.shop.myshop.Admin.AllProduct;
import com.shop.myshop.ProductsModel;
public class Products extends AllProduct {

    public Products() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return super.onCreateView( inflater,  container,
                 savedInstanceState) ;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onItemClick(ProductsModel product){
        //        super.onItemClick(postion);
        Navigation.findNavController(getView()).navigate(ProductsDirections.actionProductsToProductView(product));

    }
}
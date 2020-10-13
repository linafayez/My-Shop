package com.shop.myshop.User;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.shop.myshop.Admin.AllProduct;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.SharedPreference;

import java.util.ArrayList;

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

    public static class Cart{

        public static void AddToCart(Context context,ProductsModel productsModel) {
            SharedPreference sharedPreference = new SharedPreference(context);
            int b=0;
            ArrayList<ProductsModel> arrayList = sharedPreference.getCartData();
            if(arrayList != null){
                for(int i =0;i< arrayList.size();i++){
                    if(arrayList.get(i).getID().equals(productsModel.getID())){
                        b=1;
                        break;
                    }
                }
            }
            if(b==0) {
                sharedPreference.addToCart( productsModel);
                Toast.makeText(context, "Add to cart", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, " from past", Toast.LENGTH_LONG).show();
            }
        }

    }
}
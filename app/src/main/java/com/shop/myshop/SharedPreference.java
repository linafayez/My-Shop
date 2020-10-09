package com.shop.myshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {
    Context context;
    public SharedPreference(Context context){
        this.context =context;
    }
//    public void saveUser(UserInfo User) {
//        SharedPreferences settings;
//        SharedPreferences.Editor editor;
//
//        settings = context.getSharedPreferences("User",
//                Context.MODE_PRIVATE);
//        editor = settings.edit();
//        Gson gson = new Gson();
//        String user = gson.toJson(User);
//        editor.putString("user", user);
//
//        editor.apply();
//    }
//    public UserInfo getUser(){
//        SharedPreferences settings;
//        UserInfo userModel;
//        settings = context.getSharedPreferences("User",
//                Context.MODE_PRIVATE);
//        if (settings.contains("User")) {
//            String u = settings.getString("user", null);
//            Gson gson = new Gson();
//             userModel = gson.fromJson(u, UserInfo.class);
//        } else
//            return null;
//
//        return userModel;
//
//    }

    public void SaveCart(List<ProductsModel> pro) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences("ProductCart",
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String product = gson.toJson(pro);
        editor.putString("Product", product);

        editor.apply();
    }
    public void addToCart(ProductsModel product) {
        ArrayList<ProductsModel> productModelList = getCartData();



        if (productModelList == null )
            productModelList = new ArrayList<ProductsModel>();
        productModelList.add(product);
        SaveCart(productModelList);
    }



    public ArrayList<ProductsModel> getCartData() {
        SharedPreferences settings;
        List<ProductsModel> productModels;

        settings = context.getSharedPreferences("ProductCart",
                Context.MODE_PRIVATE);

        if (settings.contains("Product")) {
            String jsonFavorites = settings.getString("Product", null);
            Gson gson = new Gson();
            ProductsModel[] cartItem = gson.fromJson(jsonFavorites,
                    ProductsModel[].class);

            productModels = Arrays.asList(cartItem);
            productModels = new ArrayList<ProductsModel>(productModels);
        } else
            return null;

        return (ArrayList<ProductsModel>) productModels;
    }

}

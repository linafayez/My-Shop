package com.shop.myshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreference {
    static Context context;
    public SharedPreference(Context context){
        this.context =context;
    }

    public static void addUser(UserInfo userObject){
        SharedPreferences user;
        SharedPreferences.Editor editorUser;
        user = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        editorUser = user.edit();
        Gson gson = new Gson();
        String userString = gson.toJson(userObject);
        editorUser.putString("user", userString);

        editorUser.apply();
    }

    public UserInfo getUser(){
        SharedPreferences settings;
        UserInfo user;

        settings = context.getSharedPreferences("User",
                Context.MODE_PRIVATE);

        if (settings.contains("user")) {
            String u = settings.getString("user", null);
            Gson gson = new Gson();
            user = gson.fromJson(u,
                    UserInfo.class);

        }else {
            user = null;
        }

        return user;
    }
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
        if(product.getItemNumberInCart()==0){
            product.setItemNumberInCart(1);
        }
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

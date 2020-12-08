package com.shop.myshop;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.shop.myshop.Models.shopModel;

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
    public  void SaveShop(shopModel model){
        SharedPreferences shop;
        SharedPreferences.Editor editorShop;
        shop = context.getSharedPreferences("Shop", Context.MODE_PRIVATE);
        editorShop = shop.edit();
        Gson gson = new Gson();
        String userString = gson.toJson(model);
        editorShop.putString("Shop", userString);

        editorShop.apply();
    }

    public shopModel getShop(){
        SharedPreferences settings;
        shopModel shopModel;

        settings = context.getSharedPreferences("Shop",
                Context.MODE_PRIVATE);

        if (settings.contains("Shop")) {
            String u = settings.getString("Shop", null);
            Gson gson = new Gson();
            shopModel = gson.fromJson(u,
                    shopModel.class);

        }else {
            shopModel = null;
        }

        return shopModel;
    }

    public ArrayList<shopModel> getAllShop(){
        SharedPreferences settings;
        List<shopModel> shopModels;
        settings = context.getSharedPreferences("AllShops",
                Context.MODE_PRIVATE);
        if (settings.contains("shops")) {
            String u = settings.getString("shops", null);
            Gson gson = new Gson();
            shopModel[] shopCart = gson.fromJson(u,
                    shopModel[].class);

            shopModels = Arrays.asList(shopCart);
            shopModels = new ArrayList<shopModel>(shopModels);
        } else
            return new ArrayList<>();

        return (ArrayList<shopModel>) shopModels;
    }
    public void SaveAllShop(ArrayList<shopModel> models){
        SharedPreferences shop;
        SharedPreferences.Editor editorShop;
        shop = context.getSharedPreferences("AllShops", Context.MODE_PRIVATE);
        editorShop = shop.edit();
        Gson gson = new Gson();
        String ShopString = gson.toJson(models);
        editorShop.putString("shops", ShopString);
        editorShop.apply();
    }
    public void addShop(shopModel model){
        ArrayList<shopModel> ModelList = getAllShop();
        if (ModelList == null )
            ModelList = new ArrayList<shopModel>();
        ModelList.add(model);
        SaveAllShop(ModelList);
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
            return new ArrayList<>();

        return (ArrayList<ProductsModel>) productModels;
    }

}

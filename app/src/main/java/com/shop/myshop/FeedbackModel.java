package com.shop.myshop;

import com.google.firebase.Timestamp;
import java.util.ArrayList;

public class FeedbackModel {
    private ArrayList<ProductsModel> productsModels;
    private Timestamp timestamp;
    private String userId;
    private String OrderId;
    public FeedbackModel(){

    }
    FeedbackModel(ArrayList<ProductsModel> productsModels, Timestamp timestamp,String userId){
        this.productsModels =productsModels;
        this.timestamp =timestamp;
        this.userId = userId;
    }

    public ArrayList<ProductsModel> getProductsModels() {
        return productsModels;
    }

    public void setProductsModels(ArrayList<ProductsModel> productsModels) {
        this.productsModels = productsModels;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }
}

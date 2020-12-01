package com.shop.myshop;


import com.google.firebase.Timestamp;
import com.shop.myshop.Models.shopModel;

import java.io.Serializable;
import java.util.ArrayList;

public class ProductsModel implements Serializable {
    private String name;
    private int price;
    private String desc;
    private int itemNumber;
    private int itemNumberInCart;
    private String ID;
    private ArrayList<String> thumbnailPic;
    private ArrayList<String> pic;
    private String category_id;
    private transient Timestamp time;
    private boolean haveAds;
    private int discount;
    private shopModel shop;
    public ProductsModel() {
        setItemNumberInCart(0);
    }

    public ProductsModel(String ID, String name, int price, String desc, int itemNumber, ArrayList<String> pic, Timestamp time, String category_id) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.pic = pic;
        this.ID = ID;
        this.itemNumber = itemNumber;
        this.category_id = category_id;
        this.time = time;
        setItemNumberInCart(0);

    }

    public ProductsModel(String ID, String name, int price, String desc, int itemNumber, ArrayList<String> pic, Timestamp time, ArrayList<String> thumbnailPic) {
        this.name = name;
        this.price = price;
        this.desc = desc;
        this.pic = pic;
        this.ID = ID;
        this.itemNumber = itemNumber;
        this.thumbnailPic = thumbnailPic;
    }

    //    public ProductsModel(Map<String, Object> map){
//
//        this.name = ((String) map.get("name"));
//        this.price =  map.get("price") != null? Integer.valueOf(map.get("price").toString()):0;
//        this.desc = ((String) map.get("desc"));
//        this.pic = ((ArrayList<String>) map.get("pic"));
//        this.ID = ((String) map.get("ID"));
//        this.itemNumber = map.get("itemNumber") != null? Integer.valueOf(map.get("itemNumber").toString()):0;
//        this.thumbnailPic =  ((ArrayList<String>) map.get("thumbnailPic"));
//        this.time =  ((Timestamp) map.get("time"));
//    }
//
//
//
//    public Map<String, Object> getMap(){
//        return  new HashMap<String, Object>(){{
//          put("name",(Object) getName());
//          put("price",(Object) getPrice());
//            put("desc",(Object) getDesc());
//
//        }};
//
//    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public ArrayList<String> getThumbnailPic() {
        return thumbnailPic;
    }

    public void setThumbnailPic(ArrayList<String> thumbnailPic) {
        this.thumbnailPic = thumbnailPic;
    }

    public ArrayList<String> getPic() {
        return pic;
    }

    public void setPic(ArrayList<String> pic) {
        this.pic = pic;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getItemNumberInCart() {
        return itemNumberInCart;
    }

    public void setItemNumberInCart(int itemNumberInCart) {
        this.itemNumberInCart = itemNumberInCart;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public boolean isHaveAds() {
        return haveAds;
    }

    public void setHaveAds(boolean haveAds) {
        this.haveAds = haveAds;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public shopModel getShop() {
        return shop;
    }

    public void setShop(shopModel shop) {
        this.shop = shop;
    }
}
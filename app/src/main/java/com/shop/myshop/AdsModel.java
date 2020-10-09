package com.shop.myshop;

public class AdsModel {
    private String id;
    private String image;
//    private Number time;
    public AdsModel(){}
    public AdsModel(String id, String image) {
        this.id = id;
        this.image = image;
//        this.time = time;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public Number getTime() {
//        return time;
//    }
//
//    public void setTime(Number time) {
//        this.time = time;
//    }



}

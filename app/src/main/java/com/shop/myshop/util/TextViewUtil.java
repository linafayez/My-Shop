package com.shop.myshop.util;

public class TextViewUtil {

    public static String getPriceToDisplay(int price){
//        if (price == null) {
//            return "0 JD";
//        }
        return price / 100.0 + "JD";
    }
}

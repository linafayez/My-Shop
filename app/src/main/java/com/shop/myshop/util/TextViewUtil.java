package com.shop.myshop.util;

import android.text.Html;
import android.text.Spanned;

import com.shop.myshop.ProductsModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TextViewUtil {
    static DecimalFormat df2 = new DecimalFormat("#.##");
    public static String getPriceToDisplay(int price, int i){
        return df2.format(price*i / 100.0) + "JD";
    }
    public static Spanned getOldPrice(int price, int i){
        Spanned spanned =  Html.fromHtml("<del>"+TextViewUtil.getPriceToDisplay(price,i)+"</del>");
          return spanned;
    }
    public static String getDiscountToDisplay(int price,int discount, int i){
        Double p =price/100.0 ;
        p-=p*(discount/100.0);
        return df2.format(p*i)+"JD";
    }
    public static double setSubTotal(ArrayList<ProductsModel> data) {
        Double sum = 0.0;
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                ProductsModel P = data.get(i);
                double price = P.getPrice() / 100.0;
                if (P.getDiscount() != 0) {
                    price -= price * (P.getDiscount() / 100.0);
                }
                int number = P.getItemNumberInCart();
                sum += Double.parseDouble(df2.format(price * number));
                // sum += price*number;
            }
        }
          return sum;
    }

}

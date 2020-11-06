package com.shop.myshop.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.Html;
import android.text.Spanned;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.UserInfo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    public static String totalWithDis(ArrayList<ProductsModel> data,int dis){
        Double p =setSubTotal(data);
        p-=p*(dis/100.0);
        return df2.format(p)+"JD";
    }
    public static Spanned ItemsName(ArrayList<ProductsModel> data){
      String text ="<ul>";
        for(int i=0;i < data.size();i++) {
            text +="<li>"+data.get(i).getName()+ "</li>";
        }
        text+="</ul>";

        return Html.fromHtml(text);

    }

    public static String getCompleteAddressString(double LATITUDE, double LONGITUDE, Context context) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for(int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }



}

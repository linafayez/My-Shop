package com.shop.myshop.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firestore.v1.TargetOrBuilder;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyCart extends Fragment {
    RecyclerView cart;
    CartAdapter adapter;
    ArrayList<ProductsModel> data;
    SharedPreference sharedPreference;
static TextView total;
    public MyCart() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        total = view.findViewById(R.id.total);
        cart = view.findViewById(R.id.cartRe);
        sharedPreference = new SharedPreference(getContext());
        Double sum= 0.0;
        data = new ArrayList<>();
        data = sharedPreference.getCartData();
        Toast.makeText(getContext(), data.size() + "", Toast.LENGTH_LONG).show();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                ProductsModel P = data.get(i);
                if (P.getItemNumberInCart() == 0) {
                    P.setItemNumberInCart(1);
                }
                double price = P.getPrice()/100.0;
                int number = P.getItemNumberInCart();
                sum += price*number;
            }
        }
         total.setText(sum+"JD");
        adapter = new CartAdapter(getContext(), data);
        cart.setLayoutManager(manager);
        cart.setHasFixedSize(false);
        cart.setAdapter(adapter);

    }

    public static class changed {
        DecimalFormat df2 = new DecimalFormat("#.##");
        public void total(double t) {
            double price = Double.valueOf(total.getText().toString().split("JD")[0]);
            double s = price+ t;
            total.setText(df2.format(s) + "JD");
        }
    }
}
package com.shop.myshop.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    Button checkout;
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
        data = new ArrayList<>();
        data = sharedPreference.getCartData();
       // Toast.makeText(getContext(), data.size() + "", Toast.LENGTH_LONG).show();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        changed.setTotal(data);
        adapter = new CartAdapter(getContext(), data);
        cart.setLayoutManager(manager);
        cart.setHasFixedSize(false);
        cart.setAdapter(adapter);
        checkout = view.findViewById(R.id.checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Navigation.findNavController(getView()).navigate(MyCartDirections.actionMyCartToCheckOut());
            }
        });
    }

    public static class changed {
        static DecimalFormat df2 = new DecimalFormat("#.##");
        public static void total(double t) {
//            double price = Double.valueOf(total.getText().toString().split("JD")[0]);
//           t= Double.parseDouble(df2.format(t));
//            double s = price+ t;
//            total.setText(df2.format(s) + "JD");
        }
        public static void setTotal(ArrayList<ProductsModel> data){
            Double sum= 0.0;
            if (data != null) {
                for (int i = 0; i < data.size(); i++) {
                    ProductsModel P = data.get(i);
                    double price = P.getPrice() / 100.0;
                    if(P.getDiscount()!=0){
                        price-=price*(P.getDiscount()/100.0);
                    }
                    int number = P.getItemNumberInCart();
                    sum += Double.parseDouble(df2.format(price*number));
                   // sum += price*number;
                }
            }
            total.setText(df2.format(sum)+"JD");
        }
    }
}
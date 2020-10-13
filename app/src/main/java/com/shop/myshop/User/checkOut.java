package com.shop.myshop.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.util.TextViewUtil;

import java.util.ArrayList;

public class checkOut extends Fragment {
    RecyclerView items;
    ItemsAdapter adapter;
    ArrayList<ProductsModel> data;
    SharedPreference sharedPreference;
   // Button checkout;
    static TextView total,subTotal,shipping;

    public checkOut() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_out, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        items= view.findViewById(R.id.items);
        subTotal= view.findViewById(R.id.subtotal);
        sharedPreference = new SharedPreference(getContext());
        data = new ArrayList<>();
        data = sharedPreference.getCartData();
        subTotal.setText(TextViewUtil.setSubTotal(data)+"JD");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        MyCart.changed.setTotal(data);
        adapter = new ItemsAdapter(getContext(), data);
        items.setLayoutManager(manager);
        items.setHasFixedSize(false);
        items.setAdapter(adapter);
    }
}
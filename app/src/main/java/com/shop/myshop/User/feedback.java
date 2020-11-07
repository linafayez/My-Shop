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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.User.CartAdapter;
import com.shop.myshop.User.FeedBackAdapter;
import com.shop.myshop.User.MyCart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class feedback extends Fragment {
    RecyclerView products;
    FeedBackAdapter adapter;
    ArrayList<ProductsModel> data;
   Button Submit;
    public feedback() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        products = view.findViewById(R.id.pro);
        Submit = view.findViewById(R.id.Submit);
        data = feedbackArgs.fromBundle(getArguments()).getOrder().getProductsModels();
//        Gson gson = new Gson();
//        List<ProductsModel> productModels;
//        ProductsModel[] pro = gson.fromJson(getArguments().getString("products"),
//                ProductsModel[].class);
//        Toast.makeText(getContext(),pro.length+"",Toast.LENGTH_LONG).show();
//        productModels = Arrays.asList(pro);
//        data = new ArrayList<ProductsModel>(productModels);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new FeedBackAdapter(getContext(),  feedbackArgs.fromBundle(getArguments()).getOrder());
        products.setLayoutManager(manager);
        products.setHasFixedSize(false);
        products.setAdapter(adapter);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }



}
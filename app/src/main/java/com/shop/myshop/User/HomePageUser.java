package com.shop.myshop.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.littlemango.stacklayoutmanager.StackLayoutManager;
import com.shop.myshop.Models.shopModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;

import java.util.ArrayList;

public class HomePageUser extends Fragment {
    RecyclerView shops,products;
    ArrayList<ProductsModel> lastProList;
    ArrayList<shopModel> shopModels;
    shopCartAdapter shopCartAdapter;
    LastProductAdapter ProAdapter;
    public HomePageUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shops = view.findViewById(R.id.shopes);
        products = view.findViewById(R.id.all);
        lastProList = new ArrayList<>();
        shopModels = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        lastProList.add(document.toObject(ProductsModel.class));
                    }
                    ProAdapter = new LastProductAdapter(getContext(),lastProList,"user");
                    // RecyclerView.LayoutManager manager =new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                    products.setLayoutManager(layoutManager);
                    products.setHasFixedSize(false);
                    products.setAdapter(ProAdapter);
                }
            }
        });
        FirebaseFirestore.getInstance().collection("Shop").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                      shopModels.add(document.toObject(shopModel.class));
                    }
                    shopCartAdapter = new shopCartAdapter(getContext(),shopModels);
                    // RecyclerView.LayoutManager manager =new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
                    StackLayoutManager manager = new StackLayoutManager();
                    shops.setLayoutManager(manager);
                    shops.setHasFixedSize(false);
                    shops.setAdapter(shopCartAdapter);
              }
         }
        });
    }
}

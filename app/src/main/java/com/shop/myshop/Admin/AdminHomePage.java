package com.shop.myshop.Admin;

import android.annotation.SuppressLint;
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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.littlemango.stacklayoutmanager.StackLayoutManager;
import com.shop.myshop.OrderModel;
import com.shop.myshop.R;
import com.shop.myshop.User.OrderUser;
import com.shop.myshop.util.TextViewUtil;

import java.util.ArrayList;

public class AdminHomePage extends Fragment {
    TextView allOrders;
    RecyclerView order;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<OrderModel> options;
    public AdminHomePage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_home_page, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        order = view.findViewById(R.id.orders);
        allOrders = view.findViewById(R.id.AllOrder);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Orders").orderBy("time");

        options = new FirestoreRecyclerOptions.Builder<OrderModel>()
                .setQuery(query,OrderModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<OrderModel, OrderHolder>(options){

            @NonNull
            @Override
            public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_order, parent, false);
                return new OrderHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderHolder holder, int position, @NonNull OrderModel model) {
                holder.total.setText(model.getTotal());
                holder.Date.setText(model.getTime().toDate().getDay()+"/"+model.getTime().toDate().getMonth()+"/"+model.getTime().toDate().getYear());
                holder.Items.setText(TextViewUtil.ItemsName(model.getProductsModels()));
                holder.orderId.setText(model.getOrderId());
                holder.UserId.setText(model.getUserId().substring(0,10));
           }
        };

        StackLayoutManager manager = new StackLayoutManager();
        order.setLayoutManager(manager);
        order.setHasFixedSize(false);
        order.setAdapter(adapter);

    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    class OrderHolder extends RecyclerView.ViewHolder{
        TextView Date, Items , total,time, orderId,UserId , OrderState;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            OrderState = itemView.findViewById(R.id.OrderState);
            orderId = itemView.findViewById(R.id.id);
            UserId = itemView.findViewById(R.id.userId);
            time = itemView.findViewById(R.id.time);
            Date = itemView.findViewById(R.id.date);
            Items = itemView.findViewById(R.id.productList);
            total = itemView.findViewById(R.id.total);

        }
    }
}
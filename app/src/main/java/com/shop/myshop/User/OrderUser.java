package com.shop.myshop.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shop.myshop.Admin.AllProduct;
import com.shop.myshop.CategoryModel;
import com.shop.myshop.OrderModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.util.TextViewUtil;

import java.util.ArrayList;

public class OrderUser extends Fragment {
    RecyclerView order;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    ArrayList<OrderModel> orderModels;
    FirestoreRecyclerOptions<OrderModel> options;
    public OrderUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        order = view.findViewById(R.id.order);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Orders").whereEqualTo("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        options = new FirestoreRecyclerOptions.Builder<OrderModel>()
                .setQuery(query,OrderModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<OrderModel, OrderViewHolder >(options){

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_order_card, parent, false);
                return new OrderViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull OrderModel model) {
             holder.total.setText("Total ="+model.getTotal());
             holder.Date.setText(model.getTime().toDate().getDay()+"/"+model.getTime().toDate().getMonth()+"/"+model.getTime().toDate().getYear());
             holder.Items.setText(TextViewUtil.ItemsName(model.getProductsModels()));
            }
        };
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
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
    class OrderViewHolder extends RecyclerView.ViewHolder{
        TextView Date, Items , total;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.Date);
            Items = itemView.findViewById(R.id.items);
            total = itemView.findViewById(R.id.total);

        }
    }
}
package com.shop.myshop.Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.shop.myshop.shopModel;
import com.shop.myshop.util.TextViewUtil;

import java.util.ArrayList;

public class AdminHomePage extends Fragment {
    TextView allOrders;
    RecyclerView order;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<shopModel> options;
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
        Query query = db.collection("Shop").whereEqualTo("shopState","Request");

        options = new FirestoreRecyclerOptions.Builder<shopModel>()
                .setQuery(query,shopModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<shopModel, shopHolder>(options){

            @NonNull
            @Override
            public shopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_shop, parent, false);
                return new shopHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull shopHolder holder, int position, @NonNull shopModel model) {
              holder.shopName.setText("Name : "+model.getName());
//                holder.Date.setText(model.getTime().toDate().getDay()+"/"+model.getTime().toDate().getMonth()+"/"+model.getTime().toDate().getYear());
//                holder.Items.setText(TextViewUtil.ItemsName(model.getProductsModels()));
               holder.type.setText("Type of shop : "+model.getType());
               if(model.getUser()!= null)
                  holder.User.setText("User name : "+model.getUser().getName());
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
     class shopHolder extends RecyclerView.ViewHolder{
        TextView Date, User , total,time, type,UserId , shopName;
        public shopHolder(@NonNull View itemView) {
            super(itemView);
           shopName = itemView.findViewById(R.id.shopName);
            type = itemView.findViewById(R.id.type);
            User = itemView.findViewById(R.id.user);
//            time = itemView.findViewById(R.id.time);
//            Date = itemView.findViewById(R.id.date);
//            Items = itemView.findViewById(R.id.productList);
//            total = itemView.findViewById(R.id.total);
itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Navigation.findNavController(getView()).navigate(AdminHomePageDirections.actionAdminHomePageToViewOrderOfShop(options.getSnapshots().get(getAdapterPosition())));
    }
});
        }
    }
}
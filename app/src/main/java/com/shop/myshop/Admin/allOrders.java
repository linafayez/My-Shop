package com.shop.myshop.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.littlemango.stacklayoutmanager.StackLayoutManager;
import com.shop.myshop.OrderModel;
import com.shop.myshop.R;
import com.shop.myshop.util.TextViewUtil;

import java.util.ArrayList;
public class allOrders extends Fragment {
    RecyclerView order;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<OrderModel> options;
    public allOrders() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        order = view.findViewById(R.id.Orders);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Orders").orderBy("time", Query.Direction.DESCENDING);

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
               // holder.orderId.setText(model.getOrderId());
                holder.UserId.setText(model.getUserId().substring(0,10));
            }
        };

      //  RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        order.setLayoutManager( new LinearLayoutManager(getContext()));
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
   //  StepsView process;
        public OrderHolder(@NonNull View itemView) {
            super(itemView);
            ArrayList<String> list0 = new ArrayList<>();
            list0.add("req");
            list0.add("process");
            list0.add("completed");
            String[] steps ={ "req","process"};
           //            orderId = itemView.findViewById(R.id.id);
            UserId = itemView.findViewById(R.id.userId);
            time = itemView.findViewById(R.id.time);
            Date = itemView.findViewById(R.id.date);
            Items = itemView.findViewById(R.id.productList);
            total = itemView.findViewById(R.id.total);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Navigation.findNavController(itemView.getRootView()).navigate(R.id.action_allOrders_to_orderView);
                   Navigation.findNavController(getView()).navigate(allOrdersDirections.actionAllOrdersToOrderView(options.getSnapshots().get(getAdapterPosition())));
                }
            });


        }
    }
}
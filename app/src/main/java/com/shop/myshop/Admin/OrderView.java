package com.shop.myshop.Admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shop.myshop.OrderModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class OrderView extends Fragment{
    StepView stepView;
    RecyclerView items;
    ArrayList<ProductsModel> data;
    MapView mapView;
    GoogleMap googleMap;
    TextView OrderId, userId;
    OrderModel orderModel;

    public OrderView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderId = view.findViewById(R.id.id);
        items = view.findViewById(R.id.orderItems);
        userId = view.findViewById(R.id.UserId);
        orderModel = OrderViewArgs.fromBundle(getArguments()).getOrder();
        OrderId.setText(orderModel.getOrderId());
        userId.setText("UserId:" + orderModel.getUserId().substring(0, 10));
        stepView = view.findViewById(R.id.step_view);
        data = orderModel.getProductsModels();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        items.setLayoutManager(manager);
        items.setHasFixedSize(false);
   //     items.setAdapter(adapter);
        List<String> step = new ArrayList<>();
        step.add("Step 1");
        step.add("step 2");
        step.add("step 3");
        stepView.getState()
                .selectedTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                //   .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(step)
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(step.size())

                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                // .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
                //  .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
                // .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                .typeface(ResourcesCompat.getFont(getContext(), R.font.roboto_light))
                // other state methods are equal to the corresponding xml attributes
                .commit();
        stepView.go(1, true);


    }

}
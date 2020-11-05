package com.shop.myshop.Admin;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shop.myshop.OrderModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.User.ItemsAdapter;
import com.shop.myshop.UserInfo;
import com.shop.myshop.util.TextViewUtil;
import com.shuhart.stepview.StepView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderView extends Fragment{
    StepView stepView;
    RecyclerView items;
    ArrayList<ProductsModel> data;
    MapView mapView;
    GoogleMap googleMap;
    TextView OrderId, user;
    OrderModel orderModel;
    ItemsAdapter adapter;
    FloatingActionButton finish;
    TextView total , total2, phone, address , date;
UserInfo User;
Button showInMap;

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
        orderModel = OrderViewArgs.fromBundle(getArguments()).getOrder();
        OrderId = view.findViewById(R.id.id);
        date = view.findViewById(R.id.date);
        finish = view.findViewById(R.id.finish);
        showInMap = view.findViewById(R.id.button7);
        items = view.findViewById(R.id.orderItems);
        user = view.findViewById(R.id.User);
        address = view.findViewById(R.id.address);
        total = view.findViewById(R.id.total);
        total2= view.findViewById(R.id.sum);
        data = orderModel.getProductsModels();
        address.setText(TextViewUtil.getCompleteAddressString(orderModel.getLatitude(), orderModel.getLongitude(),getContext()));
        total.setText(orderModel.getTotal());
        phone = view.findViewById(R.id.phone);
        total2.setText(TextViewUtil.setSubTotal(data)+3+"JD");

        OrderId.setText(orderModel.getId());
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date javaDate = orderModel.getTime().toDate();
        showInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("geo:%f,%f"+orderModel.getLatitude()+orderModel.getLongitude());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + orderModel.getLatitude()  + ">,<" + orderModel.getLongitude() + ">?q=<" + orderModel.getLatitude()  + ">,<" + orderModel.getLongitude() + ">(" + "user location" + ")"));
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
        date.setText(javaDate+"");
        FirebaseFirestore.getInstance().collection("User").document( orderModel.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User = documentSnapshot.toObject(UserInfo.class);
                user.setText("User Name:" +User.getName());
                phone.setText("User Phone Number:"+User.getPhone());

            }
        });

        stepView = view.findViewById(R.id.step_view);

        adapter = new ItemsAdapter(getContext(), data);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        items.setLayoutManager(manager);
        items.setHasFixedSize(false);
       items.setAdapter(adapter);
        List<String> step = new ArrayList<>();
        step.add("Confirmed");
        step.add("Processing");
        step.add("Completed");
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
        for (int i=1;i<step.size();i++){
            if(orderModel.getState().equals(step.get(i))){
                stepView.go(i, true);
                break;
            }
        }
        if(orderModel.getState().equals(step.get(0))) {
           orderModel.setState(step.get(1));
        FirebaseFirestore.getInstance().collection("Orders").document(orderModel.getId()).set(orderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                stepView.go(1, true);
            }
        });
        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderModel.setState(step.get(2));
                FirebaseFirestore.getInstance().collection("Orders").document(orderModel.getId()).set(orderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        stepView.go(2, true);
                        Navigation.findNavController(getView()).navigateUp();
                        //send notification to this user
                    }
                });
            }
        });

    }

}
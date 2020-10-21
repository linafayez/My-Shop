package com.shop.myshop.User;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shop.myshop.OrderModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.util.TextViewUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class checkOut extends Fragment {
    RecyclerView items;
    OrderModel orderModel;
    ItemsAdapter adapter;
    ArrayList<ProductsModel> data;
    SharedPreference sharedPreference;
   Button addAdders, Order;
   Date date;
    double latitude ;
    double longitude ;
    static TextView total,subTotal,shipping,address;

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
        Order = view.findViewById(R.id.Order);

        items= view.findViewById(R.id.items);
        subTotal= view.findViewById(R.id.subtotal);
        total = view.findViewById(R.id.total);
        addAdders = view.findViewById(R.id.addAdders);
        shipping = view.findViewById(R.id.shipping);
        address =view.findViewById(R.id.address);
        sharedPreference = new SharedPreference(getContext());
        data = new ArrayList<>();
        data = sharedPreference.getCartData();
        subTotal.setText(TextViewUtil.setSubTotal(data)+"JD");
        total.setText(TextViewUtil.setSubTotal(data)+3+"JD");
        shipping.setText("3JD");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        MyCart.changed.setTotal(data);
        adapter = new ItemsAdapter(getContext(), data);
        items.setLayoutManager(manager);
        items.setHasFixedSize(false);
        items.setAdapter(adapter);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        }

        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = new Date();
                Timestamp timestamp =  new Timestamp(date);
                if(longitude != 0.0 && latitude != 0.0) {
                    orderModel = new OrderModel(longitude, longitude, data, total.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getUid(), "note");
                    orderModel.setTime(timestamp);
                    orderModel.setState("Confirmed");
                    UploadOrder(orderModel);
                }else {
                    goToMap();
                }
           // orderModel.setTime(timestamp);
               //UploadOrder(orderModel);
            }
        });
        addAdders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              goToMap();
            }
        });

    }

    private void goToMap() {
        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent(getActivity(),map.class);
                        startActivityForResult(intent,1);

                        // Navigation.findNavController(getView()).navigate(R.id.action_checkOut_to_map);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if(permissionDeniedResponse.isPermanentlyDenied()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Permission Denied")
                                    .setMessage("Permission to access location, you should go to settings and allow location")
                                    .setNegativeButton("cancel",null)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package",getActivity().getPackageName(),null));

                                            //startActivity(new Intent(getContext(),));
                                        }
                                    }).show();
                        }else {
                            Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }

    private void UploadOrder(OrderModel orderModel) {
        FirebaseFirestore.getInstance().collection("Orders")
                .add(orderModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
              Navigation.findNavController(getView()).navigate(R.id.action_checkOut_to_orderUser);
              sharedPreference.SaveCart(new ArrayList<ProductsModel>());
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Bundle extras =data.getExtras();
                if (extras != null) {
                     latitude = extras.getDouble("Latitude");
                     longitude = extras.getDouble("Longitude");
                    address.setVisibility(View.VISIBLE);
                    address.setText(getCompleteAddressString(latitude,longitude));

                } }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }
}
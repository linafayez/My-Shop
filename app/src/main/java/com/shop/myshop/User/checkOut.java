package com.shop.myshop.User;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
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
    EditText voucher , note;
    TickerView tickerView;
    Button addAdders, Order, ApplyVoucher;
    Date date;
    double latitude;
    String  uniqueID;
    double longitude;
    FirebaseFirestore db ;
    static TextView total, subTotal, shipping, address;

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
          tickerView = view.findViewById(R.id.tickerView);
        note = view.findViewById(R.id.note);
        db = FirebaseFirestore.getInstance();
        uniqueID = db.collection("Orders").document().getId();
        tickerView.setCharacterLists(TickerUtils.provideNumberList());
        ApplyVoucher = view.findViewById(R.id.button5);
        voucher = view.findViewById(R.id.voucher);
        items = view.findViewById(R.id.items);
        subTotal = view.findViewById(R.id.subtotal);
        addAdders = view.findViewById(R.id.addAdders);
        shipping = view.findViewById(R.id.shipping);
        address = view.findViewById(R.id.address);
        sharedPreference = new SharedPreference(getContext());
        data = new ArrayList<>();
        data = sharedPreference.getCartData();
        tickerView.setText(TextViewUtil.setSubTotal(data) + 3 + "JD");
        subTotal.setText(TextViewUtil.setSubTotal(data) + "JD");
        //total.setText();
        shipping.setText("3JD");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        MyCart.changed.setTotal(data);
        adapter = new ItemsAdapter(getContext(), data);
        items.setLayoutManager(manager);
        items.setHasFixedSize(false);
        items.setAdapter(adapter);
        ApplyVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPromoCode();
            }
        });
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = new Date();
                Timestamp timestamp = new Timestamp(date);
                if (longitude != 0.0 && latitude != 0.0) {
                    orderModel = new OrderModel(longitude, longitude, data, tickerView.getText(), FirebaseAuth.getInstance().getCurrentUser().getUid(), note.getText().toString());
                    orderModel.setTime(timestamp);
                    orderModel.setState("Confirmed");
                    UploadOrder(orderModel);
                } else {
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
                        Intent intent = new Intent(getActivity(), map.class);
                        startActivityForResult(intent, 1);

                        // Navigation.findNavController(getView()).navigate(R.id.action_checkOut_to_map);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        if (permissionDeniedResponse.isPermanentlyDenied()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Permission Denied")
                                    .setMessage("Permission to access location, you should go to settings and allow location")
                                    .setNegativeButton("cancel", null)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));

                                            //startActivity(new Intent(getContext(),));
                                        }
                                    }).show();
                        } else {
                            Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }

    private void UploadOrder(OrderModel orderModel) {
        orderModel.setId(uniqueID);
        db.collection("Orders").document(uniqueID).set(orderModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                updateProducts(orderModel.getProductsModels());
                Navigation.findNavController(getView()).navigate(R.id.action_checkOut_to_orderUser);
                sharedPreference.SaveCart(new ArrayList<ProductsModel>());
            }
        });
    }

    private void updateProducts(ArrayList<ProductsModel> products) {
        for(int i=0;i<products.size();i++){
            ProductsModel pro = products.get(i);
            pro.setItemNumber( pro.getItemNumber()-pro.getItemNumberInCart());
            pro.setItemNumberInCart(0);
            db.collection("Products").document(pro.getID()).set(pro).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            });
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    latitude = extras.getDouble("Latitude");
                    longitude = extras.getDouble("Longitude");
                    address.setVisibility(View.VISIBLE);
                    address.setText(getCompleteAddressString(latitude, longitude));

                }
            }
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

    public void getPromoCode() {
        FirebaseFirestore.getInstance().collection("PromoCode").whereEqualTo("code", voucher.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if(queryDocumentSnapshots.size()!=0){
                    Double p =TextViewUtil.setSubTotal(data);
                   int disc = Integer.parseInt(queryDocumentSnapshots.getDocuments().get(0).get("discount").toString());
                    p-=p*(disc/100.0);
                    tickerView.setAnimationDuration(700);
                    tickerView.setAnimationInterpolator(new OvershootInterpolator());
                    tickerView.setText(p+"JD");

                }
           //     Toast.makeText(getContext(),queryDocumentSnapshots.getDocuments().get(0).get("discount").toString(),Toast.LENGTH_LONG).show();

            }
        });
    }

}
package com.shop.myshop.User;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.util.TextViewUtil;

import java.util.ArrayList;

public class checkOut extends Fragment {
    RecyclerView items;
    ItemsAdapter adapter;
    ArrayList<ProductsModel> data;
    SharedPreference sharedPreference;
   Button addAdders;
    static TextView total,subTotal,shipping;

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
        items= view.findViewById(R.id.items);
        subTotal= view.findViewById(R.id.subtotal);
        addAdders = view.findViewById(R.id.addAdders);
        sharedPreference = new SharedPreference(getContext());
        data = new ArrayList<>();
        data = sharedPreference.getCartData();
        subTotal.setText(TextViewUtil.setSubTotal(data)+"JD");
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        MyCart.changed.setTotal(data);
        adapter = new ItemsAdapter(getContext(), data);
        items.setLayoutManager(manager);
        items.setHasFixedSize(false);
        items.setAdapter(adapter);
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

        }
        addAdders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String address=data.getStringExtra("address");
                Toast.makeText(getContext(),address,Toast.LENGTH_LONG).show();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
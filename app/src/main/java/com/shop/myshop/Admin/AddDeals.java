package com.shop.myshop.Admin;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.util.TextViewUtil;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;


public class AddDeals extends Fragment {
EditText discount;
TextView name, oldPrice, newPrice;
ImageView image;
ProductsModel product;
double pr;
Button back , ok;
    DecimalFormat df2 = new DecimalFormat("#.###");
Gson gson = new Gson();
    int dis = 0;
    public AddDeals() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_deals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       dis = 0;
       pr =0.0;
        name = view.findViewById(R.id.name);
        image = view.findViewById(R.id.image);
        oldPrice = view.findViewById(R.id.price);
        discount = view.findViewById(R.id.editTextNumber);
        newPrice = view.findViewById(R.id.newPrice);
        back = view.findViewById(R.id.cancel);
        ok = view.findViewById(R.id.accept);

product = AddDealsArgs.fromBundle(getArguments()).getProduct();

          name.setText(product.getName());
         Picasso.get().load(product.getPic().get(0)).into(image);
         pr =product.getPrice()/100.0;
         oldPrice.setText(oldPrice.getText()+" "+ pr+"JD");
         newPrice.setText(pr+"JD");
         discount.setText("00");
     discount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
         @Override
         public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
             if(event == null || !event.isShiftPressed()) {
                 dis = Integer.parseInt(discount.getText().toString());
//                  pr-= pr*(dis/100.0);
                 newPrice.setText(TextViewUtil.getDiscountToDisplay(product.getPrice(),dis,1));
                 return true;
             }else {
                 newPrice.setText(TextViewUtil.getDiscountToDisplay(product.getPrice(),dis,1));
                 return false;
             }
         }
     });
     ok.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             product.setDiscount(Integer.parseInt(discount.getText().toString()));
             uploadChange(product);
         }
     });
     back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Navigation.findNavController(getView()).navigateUp();
             Navigation.findNavController(getView()).navigateUp();
             Navigation.findNavController(getView()).navigateUp();
         }
     });


    }

    private void uploadChange(ProductsModel product) {
        FirebaseFirestore.getInstance().collection("Products").document(product.getID()).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"done upload deals",Toast.LENGTH_LONG).show();
                Navigation.findNavController(getView()).navigateUp();
                Navigation.findNavController(getView()).navigateUp();
                Navigation.findNavController(getView()).navigateUp();
            }
        });
    }
}
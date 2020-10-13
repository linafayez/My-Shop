package com.shop.myshop.User;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.util.TextViewUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductView extends Fragment {
Gson gson;
ProductsModel product;
    LinearLayout imageGallery;
    TextView name,price, desc, newPrice;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    Button addToCart;
    public ProductView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageGallery = (LinearLayout) view.findViewById(R.id.imageGallery);
        product = ProductViewArgs.fromBundle(getArguments()).getProduct();
          name= view.findViewById(R.id.name);
          addToCart = view.findViewById(R.id.add);
       name.setText(product.getName());
       price= view.findViewById(R.id.price);
       price.setText(TextViewUtil.getPriceToDisplay(product.getPrice(),1));
        newPrice = view.findViewById(R.id.newPrice);
        if(product.getDiscount() != 0){
           newPrice.setText(TextViewUtil.getDiscountToDisplay(product.getPrice(),product.getDiscount(),1));
        }else{
              newPrice.setVisibility(View.INVISIBLE);
        }
     desc = view.findViewById(R.id.desc);
     desc.setText(product.getDesc());
        for (int i=0;i<product.getPic().size();i++){
            mArrayUri.add(Uri.parse(product.getPic().get(i)));
        }
     setImage(mArrayUri);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Products.Cart.AddToCart(getContext(),product);
            }
        });

    }



    private View getImageView(Uri image) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 0, 0);
        imageView.setMinimumHeight(250);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(lp);
        //imageView.setImageResource(image);
        Picasso.get().load(image).into(imageView);
        return imageView;
    }
    public void setImage(ArrayList<Uri> image){
        for (int i=0;i<image.size();i++){
            imageGallery.addView(getImageView(image.get(i)));
            //  Picasso.get().load(images[i]).into(image);
        }
    }
}
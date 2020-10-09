package com.shop.myshop.User;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    TextView name,price, desc;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
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
       name.setText(product.getName());
       price= view.findViewById(R.id.price);
       price.setText(TextViewUtil.getPriceToDisplay(product.getPrice()));
     desc = view.findViewById(R.id.desc);
     desc.setText(product.getDesc());
        for (int i=0;i<product.getPic().size();i++){
            mArrayUri.add(Uri.parse(product.getPic().get(i)));
        }
     setImage(mArrayUri);

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
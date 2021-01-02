package com.shop.myshop.Admin;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.littlemango.stacklayoutmanager.StackLayoutManager;
import com.shop.myshop.Models.shopModel;
import com.shop.myshop.R;
import com.shop.myshop.User.AdsAdapter;

import com.squareup.picasso.Picasso;


public class ViewOrderOfShop extends Fragment {
RecyclerView images;
TextView shopName , type, desc ;
ImageView imageShop,imageUser;
shopModel shop;
RecyclerView.Adapter adapter;
    public ViewOrderOfShop() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_order_of_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageShop = view.findViewById(R.id.image);
        images = view.findViewById(R.id.switchImage);
        shopName = view.findViewById(R.id.Name);
        type = view.findViewById(R.id.type);
        shop= ViewOrderOfShopArgs.fromBundle(getArguments()).getShop();
        shopName.setText(shop.getName());
        type.setText(shop.getType());
        desc = view.findViewById(R.id.desc);
        desc.setText(shop.getDescription());
        if(shop.getImages()!= null){
          adapter = new RecyclerView.Adapter() {
              @NonNull
              @Override
              public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                  View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads,parent,false);
                  return new AdsAdapter.AdsViewHolder(v);
              }

              @Override
              public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
             //   ImageView image=  holder.itemView.findViewById(R.id.adsImage);

                  Picasso.get().load(Uri.parse(shop.getImages().get(position))).into((ImageView) holder.itemView.findViewById(R.id.adsImage));
              }

              @Override
              public int getItemCount() {
                  return shop.getImages().size();
              }
          };
        }
        StackLayoutManager manager = new StackLayoutManager();
        images.setLayoutManager(manager);
        images.setHasFixedSize(false);
        images.setAdapter(adapter);
        if(shop.getImage()!= null){
            Picasso.get().load(Uri.parse(shop.getImage())).into(imageShop);
        }
        
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
//
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
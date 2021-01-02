package com.shop.myshop.User;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shop.myshop.Models.shopModel;
import com.shop.myshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class shopCartAdapter extends RecyclerView.Adapter<shopCartAdapter.shopCartViewHolder>  {
    ArrayList<shopModel> shops;
    Context context;
    shopCartAdapter(Context context,ArrayList<shopModel> shops){
        this.shops = shops;
        this.context=context;

    }
    @NonNull
    @Override
    public shopCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop,parent,false);
        return new shopCartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull shopCartViewHolder holder, int position) {
        if(shops.get(position).getImage()!= null)
        Picasso.get().load(Uri.parse(shops.get(position).getImage())).into(holder.image);
        holder.name.setText(shops.get(position).getName());
        holder.desc.setText(shops.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return shops == null?0:shops.size();
    }

    public class shopCartViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name,desc;

        public shopCartViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView6);
            name = itemView.findViewById(R.id.name);
            desc = itemView.findViewById(R.id.textView28);
        }
    }
}


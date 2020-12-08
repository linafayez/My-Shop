package com.shop.myshop.User;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.shop.myshop.Models.shopModel;
import com.shop.myshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.shopViewHolder> {
    ArrayList<shopModel>  shopModels;
    ShopAdapter(ArrayList<shopModel> shopModels){
        this.shopModels = shopModels;
    }
    @NonNull
    @Override
    public shopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_cart,parent,false);
        return new shopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull shopViewHolder holder, int position) {

            holder.name.setText(shopModels.get(position).getName());
        if(shopModels.get(position).getImage()!= null) {
           Picasso.get().load(Uri.parse(shopModels.get(position).getImage())).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if(shopModels==null){
            return 0;
        }else{
            return shopModels.size();
        }
    }

    public class shopViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        TextView name;
        public shopViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView6);
            name = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(itemView).navigate(AllShopCartsDirections.actionAllShopCartsToMyCart(shopModels.get(getAdapterPosition())));
                }
            });

        }
    }
}

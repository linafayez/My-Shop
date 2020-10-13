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

import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.util.TextViewUtil;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemViewHolder> {
    ArrayList<ProductsModel> productsModels;
    DecimalFormat df2 = new DecimalFormat("#.##");
    Context context;
    public ItemsAdapter(Context context,ArrayList<ProductsModel> productsModels){
        this.context= context;
        this.productsModels = productsModels;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_card,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ProductsModel p = productsModels.get(position);
        holder.name.setText(p.getName());
        if(p.getDiscount() != 0){
            holder.newPrice.setText(TextViewUtil.getDiscountToDisplay(p.getPrice(),p.getDiscount(),p.getItemNumberInCart()));
            holder.price.setText(TextViewUtil.getOldPrice(p.getPrice(),p.getItemNumberInCart()));
        }else{
            holder.price.setText(TextViewUtil.getPriceToDisplay(p.getPrice(),p.getItemNumberInCart()));
            holder.newPrice.setVisibility(View.INVISIBLE);
        }
        Picasso.get().load(Uri.parse(p.getPic().get(0))).into(holder.image);
        holder.ItemsCount.setText("("+p.getItemNumberInCart()+")");
    }

    @Override
    public int getItemCount() {
        return productsModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView price , ItemsCount, newPrice ;
        TextView name;
        ImageView image ;
      //  SharedPreference sharedPreference = new SharedPreference(context);
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            name =itemView.findViewById(R.id.name);
            price= itemView.findViewById(R.id.price);
            newPrice = itemView.findViewById(R.id.newPrice);
            image = itemView.findViewById(R.id.image);
            ItemsCount = itemView.findViewById(R.id.count);
        }
    }
}

package com.shop.myshop.User;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
ArrayList<ProductsModel> productsModels;
    DecimalFormat df2 = new DecimalFormat("#.##");
Context context;
 public CartAdapter(Context context,ArrayList<ProductsModel> productsModels){
this.context= context;
this.productsModels = productsModels;
 }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart,parent,false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductsModel P = productsModels.get(position);
        if(P.getItemNumberInCart() == 0){
            P.setItemNumberInCart(1);
        }
        holder.ItemsCount.setText(""+P.getItemNumberInCart());
        double   price = Double.valueOf(P.getPrice()/100.0);
        int number = P.getItemNumberInCart();
        holder.price.setText(df2.format(price*number)+"JD");
        holder.name.setText(P.getName());
        Picasso.get().load(Uri.parse(P.getPic().get(0))).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return productsModels.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        TextView price , ItemsCount ;
        TextView name;
        ImageView image , del;
        Button dec, inc;
       SharedPreference sharedPreference = new SharedPreference(context);
        public CartViewHolder(@NonNull final View itemView) {
            super(itemView);

            price = (TextView) itemView.findViewById(R.id.price);
            name =(TextView) itemView.findViewById(R.id.name);
            image = (ImageView) itemView.findViewById(R.id.productImage);
            del = itemView.findViewById(R.id.imageView5);
            ItemsCount = itemView.findViewById(R.id.number);
            dec = itemView.findViewById(R.id.dec);
            inc = itemView.findViewById(R.id.inc);
          final MyCart.changed c = new MyCart.changed();
            inc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a =Integer.parseInt(ItemsCount.getText().toString());
                    if(a<20){
                        ItemsCount.setText(++a +"");
                        ProductsModel productModel =productsModels.get(getAdapterPosition());
                        productModel.setItemNumberInCart(a);
                        productsModels.set(getAdapterPosition(),productModel);
                        Double p = productModel.getPrice()/100.0*a;
                        price.setText(df2.format(p)+"JD");
                        sharedPreference.SaveCart(productsModels);
                        c.total(productModel.getPrice());
                    }
                }
            });
            dec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int a =Integer.parseInt(ItemsCount.getText().toString());
                    if(a>1){
                        ItemsCount.setText(--a +"");
                        ProductsModel productModel =productsModels.get(getAdapterPosition());
                        productModel.setItemNumberInCart(a);
                        productsModels.set(getAdapterPosition(),productModel);
                        Double p = productModel.getPrice()/100.0*a;
                        price.setText(df2.format(p)+"JD");
                        sharedPreference.SaveCart(productsModels);
                        c.total(-productModel.getPrice());

                    }
                }
            });
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductsModel productModel =productsModels.get(getAdapterPosition());
                    int a =Integer.parseInt(ItemsCount.getText().toString());
                    double   pr = Double.valueOf(price.getText().toString().split("JD")[0]);
                    c.total(-pr);
                    productsModels.remove(productModel);
                    sharedPreference.SaveCart(productsModels);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(),"Item removed", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
        }

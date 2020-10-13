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
import com.shop.myshop.util.TextViewUtil;
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
        int number = P.getItemNumberInCart();

        holder.ItemsCount.setText(""+number);
        //double   price = P.getPrice();
        if(P.getDiscount() != 0){
            holder.newPrice.setText(TextViewUtil.getDiscountToDisplay(P.getPrice(),P.getDiscount(),number));
            holder.price.setText(TextViewUtil.getOldPrice(P.getPrice(),number));
        }else{
            holder.price.setText(TextViewUtil.getPriceToDisplay(P.getPrice(),number));
            holder.newPrice.setVisibility(View.INVISIBLE);
        }
        holder.name.setText(P.getName());
        Picasso.get().load(Uri.parse(P.getPic().get(0))).into(holder.image);
    }

    @Override
    public int getItemCount() {
         if(productsModels==null){
             return 0;
         }else{
           return   productsModels.size();
         }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        TextView price , ItemsCount ,name, newPrice;
        ImageView image , del;
        Button dec, inc;
       SharedPreference sharedPreference = new SharedPreference(context);
        public CartViewHolder(@NonNull final View itemView) {
            super(itemView);
newPrice = itemView.findViewById(R.id.newPrice);
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
                        //productsModels.set(getAdapterPosition(),productModel);
                       // Double p = productModel.getPrice()/100.0;
                        sharedPreference.SaveCart(productsModels);
                        if(productModel.getDiscount()!=0) {
//                            p -= p * (productModel.getDiscount() / 100.0);
//                            p= Double.parseDouble(df2.format(p));
                            newPrice.setText(TextViewUtil.getDiscountToDisplay(productModel.getPrice(),productModel.getDiscount(),a));
                            price.setText(TextViewUtil.getOldPrice(productModel.getPrice(),a));
                        }else {
                            price.setText(TextViewUtil.getPriceToDisplay(productModel.getPrice(),a));
                        }
                        //    c.total(p);
                        c.setTotal(productsModels);


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
                       // Double p = productModel.getPrice()/100.0;
                        sharedPreference.SaveCart(productsModels);
                        if(productModel.getDiscount()!=0) {
                            //p -= p * (productModel.getDiscount() / 100.0);

                            newPrice.setText(TextViewUtil.getDiscountToDisplay(productModel.getPrice(),productModel.getDiscount(),a));
                            price.setText(TextViewUtil.getOldPrice(productModel.getPrice(),a));
                        }else {
                            price.setText(TextViewUtil.getPriceToDisplay(productModel.getPrice(),a));
                        }
                      //  c.total(-p);
                        c.setTotal(productsModels);


                    }
                }
            });
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ProductsModel productModel =productsModels.get(getAdapterPosition());
//                    int a =Integer.parseInt(ItemsCount.getText().toString());
//                    double   pr = Double.valueOf(price.getText().toString().split("JD")[0]);
//                    if(productModel.getDiscount()!=0){
//                        pr = Double.valueOf(newPrice.getText().toString().split("JD")[0]);
//                    }
//                 //   c.total(-pr);
                    productsModels.remove(productsModels.get(getAdapterPosition()));
                    sharedPreference.SaveCart(productsModels);
                    c.setTotal(productsModels);
                    notifyDataSetChanged();
                    Toast.makeText(view.getContext(),"Item removed", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }
        }

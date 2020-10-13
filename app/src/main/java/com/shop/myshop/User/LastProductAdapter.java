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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.util.TextViewUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LastProductAdapter extends RecyclerView.Adapter<LastProductAdapter.LastViewHolder> {
ArrayList<ProductsModel> productsModels;
    Context context;
    SharedPreference sharedPreference ;
public LastProductAdapter(Context context,ArrayList<ProductsModel> productsModels){
    this.context = context;
    this.productsModels = productsModels;
    sharedPreference = new SharedPreference(context);
}
    @NonNull
    @Override
    public LastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false);
        return new LastViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LastViewHolder holder, int position) {
    ProductsModel model =productsModels.get(position);
        Picasso.get().load(Uri.parse(model.getPic().get(0))).into(holder.image);
        holder.price.setText(TextViewUtil.getPriceToDisplay(model.getPrice(),1));
        holder.name.setText(model.getName());
        if(model.getDiscount() != 0){
            holder.newPrice.setText(TextViewUtil.getDiscountToDisplay(model.getPrice(),model.getDiscount(),1));
        }else{
            holder.newPrice.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return productsModels.size();
    }

    public class LastViewHolder  extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price , newPrice;
        Button AddToCart;
        ProductsModel productsModel;
        public LastViewHolder(@NonNull final View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView4);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            AddToCart = itemView.findViewById(R.id.add);
            newPrice = itemView.findViewById(R.id.newPrice);
            AddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     productsModel = productsModels.get(getAdapterPosition());
                    int b=0;
                    ArrayList<ProductsModel> arrayList = sharedPreference.getCartData();
                     if(arrayList != null){
                         for(int i =0;i< arrayList.size();i++){
                             if(arrayList.get(i).getID().equals(productsModel.getID())){
                                 b=1;
                                 break;
                             }
                         }
                     }
                    if(b==0) {
                        sharedPreference.addToCart( productsModel);
                        Toast.makeText(context, "Add to cart", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(context, " from past", Toast.LENGTH_LONG).show();
                    }

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productsModel = productsModels.get(getAdapterPosition());
                    Navigation.findNavController(itemView).navigate(MainPageDirections.actionMainPageToProductView(productsModel));
                }
            });


        }
    }
}

package com.shop.myshop.User;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shop.myshop.AdsModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.AdsViewHolder> {
    ArrayList<AdsModel> adsList;
    Context context;
    public AdsAdapter(ArrayList<AdsModel> adsList, Context context){
        this.adsList = adsList;
        this.context= context;
    }
    @NonNull
    @Override
    public AdsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ads,parent,false);
        return new AdsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdsViewHolder holder, int position) {
       // AdsModel ads = adsList.get(position);
       Picasso.get().load(Uri.parse(adsList.get(position).getImage())).into(holder.image);
    }

    @Override
    public int getItemCount() {
        if(adsList == null){
                 return 0;
        }
        return adsList.size();
    }

    public class AdsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        Bundle bundle = new Bundle();
       // Gson gson = new Gson();
        public AdsViewHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.adsImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    FirebaseFirestore.getInstance().collection("Products").document(adsList.get(getAdapterPosition()).getId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            //String Product = gson.toJson(documentSnapshot.getData());
                          //  bundle.putString("product",Product);
                          //  Navigation.createNavigateOnClickListener(R.id.action_mainPage_to_productView,bundle).onClick(v);
                   Navigation.findNavController(itemView).navigate(MainPageDirections.actionMainPageToProductView( documentSnapshot.toObject(ProductsModel.class)));
                        }
                    });
                }
            });
        }
    }
}

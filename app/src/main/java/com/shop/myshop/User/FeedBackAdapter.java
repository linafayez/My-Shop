package com.shop.myshop.User;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shop.myshop.FeedbackModel;
import com.shop.myshop.OrderModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.feedBackViewHolder> {
    static FeedbackModel feedbackModel;
    static ArrayList<ProductsModel> models;
    static OrderModel OrderModel;
    ArrayList<ProductsModel> productsModels, pro;
    Context context;
static String UID;

    public FeedBackAdapter(Context context, OrderModel orderModel) {
        OrderModel = orderModel;
        this.context = context;
        models = new ArrayList<>();
        productsModels = orderModel.getProductsModels();
       pro = feedBackViewHolder.Change.getProducts(productsModels);
    }

    @NonNull
    @Override
    public feedBackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback, parent, false);
        return new feedBackViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull feedBackViewHolder holder, int position) {
        holder.name.setText(productsModels.get(position).getName());
        Picasso.get().load(Uri.parse(productsModels.get(position).getPic().get(0))).into(holder.image);

    }

    @Override
    public int getItemCount() {
        if (productsModels == null) {
            return 0;
        } else {
            return productsModels.size();
        }
    }

    public static class feedBackViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RatingBar rating;
        ImageView image;
        TextInputLayout note;
        FloatingActionButton done;

        public feedBackViewHolder(@NonNull View itemView) {
            super(itemView);
            feedbackModel = new FeedbackModel();
            if(OrderModel.getFeedbackId() == null) {
                UID = FirebaseFirestore.getInstance().collection("Feedback").document().getId();
            }else {
                UID = OrderModel.getFeedbackId();
            }
            name = itemView.findViewById(R.id.textView20);
            rating = itemView.findViewById(R.id.ratingBar);
            image = itemView.findViewById(R.id.state);
            note = itemView.findViewById(R.id.note);
            done = itemView.findViewById(R.id.done);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 // updateProducts(pro.get(getAdapterPosition()),rating.getRating(),note.getEditText().getText().toString());
                 //   Toast.makeText(itemView.getContext(), rating.getRating() + "", Toast.LENGTH_LONG).show();
                }
            });
       //     feedbackModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());


        }
        public static class Change{
            public static void updateProducts( ProductsModel pro , Float rating , String note) {
                Date date = new Date();
                Timestamp timestamp = new Timestamp(date);
                pro.setRating(rating);
                pro.setNote(note);
                FirebaseFirestore.getInstance().collection("Products").document(pro.getID()).set(pro).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        models.add(pro);
                        feedbackModel.setProductsModels(models);
                        feedbackModel.setTimestamp(timestamp);
                        feedbackModel.setOrderId(pro.getID());
                    }
                });
            }
            private static ArrayList<ProductsModel> getProducts(ArrayList<ProductsModel> products) {
                ArrayList<ProductsModel> models= new ArrayList<>();
                for(int i= 0 ;i<products.size();i++){
                    FirebaseFirestore.getInstance().collection("Products").document(products.get(i).getID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            models.add(documentSnapshot.toObject(ProductsModel.class));
                        }
                    });
                }

                return models;
            }

        }
    }



}

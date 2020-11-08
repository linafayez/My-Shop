package com.shop.myshop.User;

import android.content.Context;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
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
    static ArrayList<ProductsModel> productsModels;
    static ArrayList<ProductsModel> pro;
    Context context;
static String UID;

    public FeedBackAdapter(Context context, OrderModel orderModel) {
        OrderModel = orderModel;
        this.context = context;
        models = new ArrayList<>();
        productsModels = orderModel.getProductsModels();
       pro = feedback.Change.getProducts(productsModels);
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

        public feedBackViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textView20);
            rating = itemView.findViewById(R.id.ratingBar);
            image = itemView.findViewById(R.id.state);
            note = itemView.findViewById(R.id.note);

       //     feedbackModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                }
            });
            note.getEditText().addTextChangedListener(new TextWatcher() {
                CountDownTimer timer = null;
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if (timer != null) {
                        timer.cancel();
                    }

                    timer = new CountDownTimer(1500, 10000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {

                            //do what you wish
                            if(rating.getNumStars()!=0){
                                //productsModels
                                feedback.Change.updateProducts(pro.get(getAdapterPosition()),rating.getRating(),note.getEditText().getText().toString(), getAdapterPosition());
                            }

                        }

                    }.start();
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (timer != null) {
                        timer.cancel();
                    }

                    timer = new CountDownTimer(1500, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {

                            //do what you wish
                            if(rating.getNumStars()!=0&& !note.getEditText().getText().equals(null)){
                                //productsModels
                                feedback.Change.updateProducts(pro.get(getAdapterPosition()),rating.getRating(),note.getEditText().getText().toString(), getAdapterPosition());
                            }

                        }

                    }.start();
                }

                @Override
                public void afterTextChanged(Editable s) {



                }
            });


        }

    }



}

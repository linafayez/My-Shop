package com.shop.myshop.User;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.shop.myshop.FeedbackModel;
import com.shop.myshop.OrderModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.User.CartAdapter;
import com.shop.myshop.User.FeedBackAdapter;
import com.shop.myshop.User.MyCart;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class feedback extends Fragment {
    static String UID ;
    RecyclerView products;
    FeedBackAdapter adapter;
    static ArrayList<ProductsModel> data;
   Button Submit;
   static OrderModel orderModel;
   static FeedbackModel feedbackModel;
   static ArrayList<ProductsModel> models , pro;
    FloatingActionButton done;
ProgressBar progressBar;
    public feedback() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressBar7);
        models = new ArrayList<>();
        pro = new ArrayList<>();
        orderModel = feedbackArgs.fromBundle(getArguments()).getOrder();
        products = view.findViewById(R.id.pro);
        Submit = view.findViewById(R.id.Submit);
        data = feedbackArgs.fromBundle(getArguments()).getOrder().getProductsModels();
        feedbackModel = new FeedbackModel();
        if(orderModel.getFeedbackId() == null) {
            UID = FirebaseFirestore.getInstance().collection("Feedback").document().getId();
        }else {
            UID = orderModel.getFeedbackId();
        }
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new FeedBackAdapter(getContext(),orderModel  );
        products.setLayoutManager(manager);
        products.setHasFixedSize(false);
        products.setAdapter(adapter);
        pro= Change.getProducts(data);
        done = view.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                Change.uploadFeedBack(feedbackModel,getView());

            }
        });
    }
    public static class Change{
        public static void uploadFeedBack(FeedbackModel feedbackModel, View view){
           Date date = new Date();
            Timestamp timestamp = new Timestamp(date);
            feedbackModel.setOrderId(UID);
            feedbackModel.setTimestamp(timestamp);
            feedbackModel.setProductsModels(models);
            feedbackModel.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());

           for (int i=0;i<models.size();i++) {
               FirebaseFirestore.getInstance().collection("Products").document(models.get(i).getID()).set(models.get(i)).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {

                   }
               });
           }
            FirebaseFirestore.getInstance().collection("Feedback").document(UID).set(feedbackModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(view.getContext(), "thank you",Toast.LENGTH_LONG).show();
                    Navigation.findNavController(view).navigateUp();
                }
            });
        }

        public static void updateProducts( ProductsModel product , Float rating , String note,int position) {
         int a=0;
         ArrayList<String> notes ;
         if(product.getNote() == null) {
             notes =new ArrayList<>();
         }else {
             notes = product.getNote();
         }
            notes.add(note);
            product.setRating(rating);
            product.setNote(notes);
//            FirebaseFirestore.getInstance().collection("Products").document(pro.getID()).set(pro).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    models.add(pro);
//                }
//            });
            data.set(position,product);

            if(models.size()>0){
                product.setRating((product.getRating()+rating)/2);
                for (int i = 0; i < models.size(); i++) {
                    if (product.getID().equals(models.get(i).getID())){
                        models.set(i,product);
                        a=1;
                        break;
                    }
                }
            }
            if(a==0){
                models.add(product);
                System.out.println("done1");
            }




        }
        public static ArrayList<ProductsModel> getProducts(ArrayList<ProductsModel> products) {
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
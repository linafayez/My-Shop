package com.shop.myshop.Admin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.shop.myshop.Admin.AdminProfileDirections;
import com.shop.myshop.Category;
import com.shop.myshop.CategoryDirections;
import com.shop.myshop.CategoryModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.Admin.AllProductArgs;
import com.shop.myshop.Admin.AllProductDirections;
import com.shop.myshop.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class AllProduct extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<ProductsModel> options;
    Query query;
    Uri ImageUri;
    ImageView image;
    String imageString;
    Dialog Ads;
    public AllProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.re);
       // Ads = new Dialog(getContext());
      //  Ads.setContentView(R.layout.ads);
        Toast.makeText(getContext(), "s", Toast.LENGTH_LONG).show();
      //  image = Ads.findViewById(R.id.adsImage);
        String type = AllProductArgs.fromBundle(getArguments()).getType();
        if (!type.equals("lastProduct")) {
            String s = AllProductArgs.fromBundle(getArguments()).getCategoryId();
             query = db.collection("Products").whereEqualTo("category_id",s).orderBy("category_id");

        } else {
            query = db.collection("Products");
        }
        options = new FirestoreRecyclerOptions.Builder<ProductsModel>()
                .setQuery(query, ProductsModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ProductsModel, ProductsHolders>(options) {

            @NonNull
            @Override
            public ProductsHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, parent, false);
                return new ProductsHolders(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsHolders holder, int position, @NonNull ProductsModel model) {
              Picasso.get().load(Uri.parse(model.getPic().get(0))).into(holder.image);
                holder.price.setText(model.getPrice() / 100.0 + "JD");
                holder.name.setText(model.getName());
            }
        };
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    class ProductsHolders extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, price;

        public ProductsHolders(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView4);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                // final String s= getArguments().getString("type");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ProductsModel product = options.getSnapshots().get(getAdapterPosition());

                    onItemClick(product);
                               }
            });


        }
    }

    public void onItemClick(ProductsModel product){

        String type = AllProductArgs.fromBundle(getArguments()).getType();
        if ( type.equals("editProduct") ) {
            goToAddProduct(product);
        }else if(type.equals("Ads")){
            displayAlert(product);
        }else if(type.equals("view")){
            //          Navigation.findNavController(getView()).navigate(AllProductDirections.actionAllProductToProductView2(product));
        }else if(type.equals("deals")){
            //   Navigation.findNavController(getView()).navigate(AllProductDirections.actionAllProduct2ToAddDeals(product));
        }
    }
    private void goToAddProduct(ProductsModel product) {
      Navigation.findNavController(getView()).navigate(AllProductDirections.actionAllProductToAddProduct(product,product.getCategory_id()));
    }

    public void displayAlert(final ProductsModel product ){

        new AlertDialog.Builder(getContext())
                .setTitle("Add Ads for product: "+product.getName())
                .setMessage("Do you wont add ads for this product?")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
               Navigation.findNavController(getView()).navigate(AllProductDirections.actionAllProductToAds(product));
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        })
                .show();
    }

}


package com.shop.myshop.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.shop.myshop.User.Products;
import com.shop.myshop.util.TextViewUtil;
import com.squareup.picasso.Picasso;

public class AllProduct extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<ProductsModel> options;
    Query query;
    TextView productsType;
    String type;

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
        productsType = view.findViewById(R.id.productsType);
       // Ads = new Dialog(getContext());
      //  Ads.setContentView(R.layout.ads);
      //  Toast.makeText(getContext(), "s", Toast.LENGTH_LONG).show();
      //  image = Ads.findViewById(R.id.adsImage);
        String type = AllProductArgs.fromBundle(getArguments()).getType();
        if (!type.equals("lastProduct") && !type.equals("lastDeals")) {
            String s = AllProductArgs.fromBundle(getArguments()).getCategoryId();
            productsType.setText(AllProductArgs.fromBundle(getArguments()).getCategoryName());
             query = db.collection("Products").whereEqualTo("category_id",s).orderBy("category_id");

        } else {
            query = db.collection("Products");
            productsType.setText(type);
        }
        if(type.equals("lastDeals")){
            query = db.collection("Products").whereGreaterThan("discount",0);
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
                holder.name.setText(model.getName());
                if(model.getDiscount() != 0){
                    holder.price.setText(TextViewUtil.getDiscountToDisplay(model.getPrice(),model.getDiscount(),1));
                    holder.newPrice.setText(TextViewUtil.getOldPrice(model.getPrice(),1));
                }else{
                    holder.price.setText(TextViewUtil.getPriceToDisplay(model.getPrice(),1));
                    holder.newPrice.setVisibility(View.INVISIBLE);
                }
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
        TextView name, price ,newPrice;
      // Button addToCart;
       FloatingActionButton addToCart;
        public ProductsHolders(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView4);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            addToCart = itemView.findViewById(R.id.floatingActionButton);
            newPrice = itemView.findViewById(R.id.newPrice);
            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  options.getSnapshots().get(getAdapterPosition()).setItemNumberInCart(1);
                    Products.Cart.AddToCart(getContext(),options.getSnapshots().get(getAdapterPosition()));
                }
            });
            final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
             type = AllProductArgs.fromBundle(getArguments()).getType();
            if(type != "view"){
                addToCart.setVisibility(View.INVISIBLE);
            }

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


        if ( type.equals("editProduct") ) {
            goToAddProduct(product);
        }else if(type.equals("Ads")){

           Navigation.findNavController(getView()).navigate(AllProductDirections.actionAllProductToAds(product));
          //  displayAlert(product);
        }else if(type.equals("view")){
            //          Navigation.findNavController(getView()).navigate(AllProductDirections.actionAllProductToProductView2(product));
        }else if(type.equals("deals")){
           Navigation.findNavController(getView()).navigate(AllProductDirections.actionAllProductToAddDeals(product));
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


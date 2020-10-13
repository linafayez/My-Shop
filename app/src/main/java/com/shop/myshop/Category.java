package com.shop.myshop;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class Category extends Fragment {
RecyclerView recyclerView;
FirebaseFirestore db;
FirestoreRecyclerAdapter adapter;
FirestoreRecyclerOptions<CategoryModel> options;
    public Category() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        recyclerView = view.findViewById(R.id.categories);
        Query query = db.collection("Category");
        options = new FirestoreRecyclerOptions.Builder<CategoryModel>()
                .setQuery(query,CategoryModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<CategoryModel,CategoryHolder>(options) {

            @NonNull
            @Override
            public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category,parent,false);
                return new CategoryHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CategoryHolder holder, int position, @NonNull CategoryModel model) {
                Picasso.get().load(Uri.parse(model.getImage())).into(holder.image);
            }
        };
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
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
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    class CategoryHolder extends RecyclerView.ViewHolder{
          ImageView image;
        public CategoryHolder(@NonNull final View itemView) {
            super(itemView);
            final Bundle bundle = new Bundle();
            image = itemView.findViewById(R.id.image);
    final String type = CategoryArgs.fromBundle(getArguments()).getType();
           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                  String id = options.getSnapshots().get(getAdapterPosition()).getId();
                  String name= options.getSnapshots().get(getAdapterPosition()).getName();
                       if (type.equals("newProduct")) {
                           goToAddProduct(id,null);
                    } else if (type.equals("editCategory")) {
                           editCategory(options.getSnapshots().get(getAdapterPosition()));
                             } else if(type.equals("editProduct")){
                           toAllProduct(id,"editProduct",name);
                       }else if(type.equals("Ads")){
                           toAllProduct(id,"Ads",name);
                       }else if(type.equals("deals")){
                           toAllProduct(id,"deals",name);
                       }
                   }

           });

        }
    }

    private void toAllProduct(String id, String type,String name) {
        Navigation.findNavController(getView()).navigate(CategoryDirections.actionCategoryToAllProduct2(type,id,name));
    }


    private void editCategory(CategoryModel category) {
   Navigation.findNavController(getView()).navigate(CategoryDirections.actionCategoryToAddCategories(category));
    }

    private void goToAddProduct(String id,ProductsModel product) {
        Navigation.findNavController(getView()).navigate(CategoryDirections.actionCategoryToAddProduct(product,id));



    }
}
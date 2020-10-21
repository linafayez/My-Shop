package com.shop.myshop.User;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.shop.myshop.Category;
import com.shop.myshop.CategoryModel;
import com.shop.myshop.R;
import com.squareup.picasso.Picasso;

public class UserCategory extends Fragment {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirestoreRecyclerAdapter adapter;
    FirestoreRecyclerOptions<CategoryModel> options;
    public UserCategory() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.categories);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("Category");
        options = new FirestoreRecyclerOptions.Builder<CategoryModel>()
                .setQuery(query,CategoryModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<CategoryModel, CategoryHolder>(options) {

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
            image = itemView.findViewById(R.id.image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
              Navigation.findNavController(getView()).navigate(UserCategoryDirections.actionUserCategoryToProducts(options.getSnapshots().get(getAdapterPosition()).getId(),"view",options.getSnapshots().get(getAdapterPosition()).getName()));
            }

            });
        }
    }
}

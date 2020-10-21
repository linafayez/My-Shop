package com.shop.myshop.Admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shop.myshop.CategoryDirections;
import com.shop.myshop.MainActivity;
import com.shop.myshop.R;
import com.shop.myshop.StartPage;
import com.shop.myshop.UserInfo;

public class AdminProfile extends Fragment {
    Button AddCategory,AddProduct, editProduct,editCategory, logOut, Ads, deals;
    TextView name, phone;
    public AdminProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        name = view.findViewById(R.id.textView6);
        phone = view.findViewById(R.id.textView7);
        deals = view.findViewById(R.id.deals);
        logOut = view.findViewById(R.id.out);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
               // Navigation.findNavController(getView()).navigateUp();
                Intent out = new Intent(getActivity(), StartPage.class);
                startActivity(out);
            }
        });
        AddCategory = view.findViewById(R.id.newCategory);
        AddProduct = view.findViewById(R.id.newProduct);
        editCategory = view.findViewById(R.id.editCategory);
        Ads = view.findViewById(R.id.Ads);
        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getView()).navigate(AdminProfileDirections.actionAdminProfileToAddCategories(null));
            }
        });
        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCategory("newProduct");
            }
        });
        editProduct = view.findViewById(R.id.edit);
        editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCategory("editProduct");
            }
        });
        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToCategory("editCategory");
            }
        });
        Ads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToCategory("Ads");
            }
        });
        deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   bundle.putString("type","deals");
             goToCategory("deals");
            }
        });
        FirebaseFirestore.getInstance().collection("User").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                UserInfo user = documentSnapshot.toObject(UserInfo.class);
                name.setText(user.getName());
                phone.setText(user.getPhone()+"");

            }
        });
                //.whereEqualTo("type","Admin").
    }

    private void goToCategory(String type) {
        Navigation.findNavController(getView()).navigate(AdminProfileDirections.actionAdminProfileToCategory(type));
    }

}
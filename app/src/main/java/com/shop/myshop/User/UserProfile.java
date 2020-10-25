package com.shop.myshop.User;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shop.myshop.R;
import com.shop.myshop.StartPage;
import com.shop.myshop.UserInfo;

public class UserProfile extends Fragment {
    TextView name, phone;
    LinearLayout userInfo , LogOut , changePassword;
    UserInfo user;
    public UserProfile() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfo = view.findViewById(R.id.userInfo);
        LogOut = view.findViewById(R.id.LogOut);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore.getInstance().collection("User").document(currentUser.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user = documentSnapshot.toObject(UserInfo.class);
                name.setText(user.getName());
                phone.setText(user.getPhone()+"");

            }
        });
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(UserProfileDirections.actionUserProfileToUserInfoPage(user));
            }
        });

        name = view.findViewById(R.id.textView6);
        phone = view.findViewById(R.id.textView7);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                getActivity().finish();
                // Navigation.findNavController(getView()).navigateUp();
//                Intent out = new Intent(getActivity(), StartPage.class);
//                startActivity(out);
            }
        });
        changePassword = view.findViewById(R.id.ChangePassword);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_userProfile_to_changePassword);
            }
        });
    }
}
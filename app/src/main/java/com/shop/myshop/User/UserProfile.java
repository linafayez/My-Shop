package com.shop.myshop.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.shop.myshop.MainActivity;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.StartPage;
import com.shop.myshop.UserInfo;

public class UserProfile extends Fragment {
    TextView name, phone;
    LinearLayout userInfo , LogOut , changePassword, req;
    UserInfo user;
    SharedPreference sharedPreference;
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
         sharedPreference = new SharedPreference(getContext());
         user = sharedPreference.getUser();
        req = view.findViewById(R.id.req);
//        userInfo = view.findViewById(R.id.userInfo);
       LogOut = view.findViewById(R.id.LogOut);
//        userInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(getView()).navigate(UserProfileDirections.actionUserProfileToUserInfoPage(user));
//            }
//        });
//
//        name = view.findViewById(R.id.textView6);
//        phone = view.findViewById(R.id.textView7);
//
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlert();

            }
        });
//        changePassword = view.findViewById(R.id.ChangePassword);
//        changePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(getView()).navigate(R.id.action_userProfile_to_changePassword);
//            }
//        });
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getView()).navigate(R.id.action_userProfile_to_requestPage);
            }
        });
    }
    public void displayAlert(){

        new AlertDialog.Builder(getContext())
                .setTitle("Log out")
                .setMessage("Are you sure to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        sharedPreference.addUser(null);
                        sharedPreference.SaveShop(null);
                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(getActivity(),
                                StartPage.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        })
                .show();
    }
}
package com.shop.myshop.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;
import com.shop.myshop.R;
import com.shop.myshop.UserInfo;


public class UserInfoPage extends Fragment {
    TextInputLayout name,email,phone;
    UserInfo user;
    public UserInfoPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_info_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
       user = UserInfoPageArgs.fromBundle(getArguments()).getUser();
       name.getEditText().setText(user.getName());
       email.getEditText().setText(user.getEmail());


    }
}
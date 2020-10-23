package com.shop.myshop.User;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shop.myshop.R;

import static com.google.firebase.auth.EmailAuthProvider.*;

public class ChangePassword extends Fragment {
    private FirebaseUser user;
    Button change;
    EditText newPass, confirm ;
    AuthCredential credential;
    public ChangePassword() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newPass = view.findViewById(R.id.newPassWord);
        confirm = view.findViewById(R.id.confirmPassword);
        change = view.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(newPass.getText().toString().equals(confirm.getText().toString())) {

                            changePass(newPass.getText().toString());
                        } else {
                            Toast.makeText(getContext(), "Not matching", Toast.LENGTH_LONG).show();

                        }

            }
        });

    }

    private void changePass(String pass) {
        change.setVisibility(View.INVISIBLE);
        user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(pass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           Toast.makeText(getContext(),"User password updated.",Toast.LENGTH_LONG).show();
                            Navigation.findNavController(getView()).navigateUp();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                change.setVisibility(View.VISIBLE);
            }
        });
    }
}
package com.shop.myshop.Auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.shop.myshop.R;

public class ForgotPassword extends Fragment {
Button back , send;
EditText email;
ProgressBar done;
FirebaseAuth auth;

    public ForgotPassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        back = view.findViewById(R.id.button4);
        email = view.findViewById(R.id.editTextTextEmailAddress);
        send = view.findViewById(R.id.button2);
         done = view.findViewById(R.id.progressBar3);
         done.setVisibility(View.INVISIBLE);
          auth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Navigation.findNavController(view).navigateUp());
                //  Toast.makeText(getContext(),"kjhg",Toast.LENGTH_LONG).show();
            }
        });
      send.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String emailAddress = email.getText().toString();
              if(!emailAddress.isEmpty()) {
             done.setVisibility(View.VISIBLE);
              send.setVisibility(View.INVISIBLE);
           auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Navigation.createNavigateOnClickListener(R.id.action_forgotPassword_to_done).onClick(getView());
                            } else {
                                done.setVisibility(View.VISIBLE);
                                Toast.makeText(getContext(), "This email doesn't have an account", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }else{
            Toast.makeText(getContext(), "Enter the email!", Toast.LENGTH_LONG).show();

        }
    }

      });


    }
}
package com.shop.myshop.Auth;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shop.myshop.AdminActivity;
import com.shop.myshop.MainActivity;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.UserInfo;

import java.util.concurrent.Executor;


public class SignUp extends Fragment {
   Button login,signUp;
    EditText email, password, ConPass, name;
   private FirebaseAuth mAuth;
    ProgressBar progressBar;
   UserInfo userdata = new UserInfo();
   FirebaseFirestore db = FirebaseFirestore.getInstance();
    public SignUp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       login = view.findViewById(R.id.button4);
        name = view.findViewById(R.id.editTextTextPersonName);
        mAuth = FirebaseAuth.getInstance();
        email = view.findViewById(R.id.editTextTextEmailAddress);
        password = view.findViewById(R.id.editTextTextPassword2);
        ConPass =  view.findViewById(R.id.editTextTextPassword3);
       signUp = view.findViewById(R.id.button2);
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              if(Navigation.findNavController(view).navigateUp());
                //  Toast.makeText(getContext(),"kjhg",Toast.LENGTH_LONG).show();
           }
       });
       signUp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

             startSignUp();
           }
       });


    }
    private void startSignUp() {
        String e = email.getText().toString();
        String p = password.getText().toString();
        String con = ConPass.getText().toString();
        String n = name.getText().toString();
        if (TextUtils.isEmpty(e) || TextUtils.isEmpty(p) || TextUtils.isEmpty(con)|| TextUtils.isEmpty(n)) {
            Toast.makeText(getContext(), "fill the fields", Toast.LENGTH_LONG).show();



        } else {
            if (!p.equals(con)) {
                Toast.makeText(getContext(), "the Password & cofirm Password should be same", Toast.LENGTH_LONG).show();

            } else {
                signUp.setVisibility(View.INVISIBLE);
              mAuth.createUserWithEmailAndPassword(e,p).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                  @Override
                  public void onSuccess(AuthResult authResult) {
                    regester(mAuth.getCurrentUser());

                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                      name.setText("");
                      email.setText("");
                      password.setText("");
                      ConPass.setText("");
                      signUp.setVisibility(View.VISIBLE);
                  }
              });
            }
        }
    }
    private void regester(@Nullable final FirebaseUser user){
        userdata.setEmail(email.getText().toString());
        assert user != null;
        userdata.setId(user.getUid());
        userdata.setName(name.getText().toString());
        userdata.setType("User");

        db.collection("User").document(user.getUid()).set(userdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(),"done",Toast.LENGTH_SHORT).show();
                updateUI(user);
             //   SharedPreference sharedPreference = new SharedPreference(getContext());
              //  sharedPreference.saveUser(userdata);
            }
        });


    }


    private void updateUI(@Nullable FirebaseUser user) {
        Intent done = new Intent(getActivity(), MainActivity.class);
        startActivity(done);
        getActivity().finish();
    }

}
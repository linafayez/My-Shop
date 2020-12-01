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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shop.myshop.AdminActivity;
import com.shop.myshop.MainActivity;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.Shop.HomePageShop;
import com.shop.myshop.UserInfo;
import com.shop.myshop.Models.shopModel;


public class SignIn extends Fragment  {
    Button login,signUp,restPass;
    private FirebaseAuth mAuth;
    EditText email,password ;
    FirebaseFirestore db;
    SharedPreference sharedPreference;

    public SignIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      //  final NavController navController = Navigation.findNavController(getActivity(), R.id.auth_nav);
        login = view.findViewById(R.id.button);
         sharedPreference = new SharedPreference(getContext());
        db = FirebaseFirestore.getInstance();
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.editTextTextPassword);
        mAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignIn();
            }
        });
        signUp = view.findViewById(R.id.button3);
        View.OnClickListener s =Navigation.createNavigateOnClickListener(R.id.action_signIn2_to_signUp2);
        signUp.setOnClickListener(s);
         restPass= view.findViewById(R.id.forget);
        View.OnClickListener f =Navigation.createNavigateOnClickListener(R.id.action_signIn2_to_forgotPassword);
        restPass.setOnClickListener(f);


    }
    private void startSignIn(){
        String e = email.getText().toString();
        String p = password.getText().toString();
        if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p)){
            Toast.makeText(getContext(), "email or password is empty", Toast.LENGTH_LONG).show();

        }else {
          login.setVisibility(View.INVISIBLE);
         mAuth.signInWithEmailAndPassword(e,p).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
             @Override
             public void onSuccess(AuthResult authResult) {

                 updateUI(mAuth.getCurrentUser());
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 login.setVisibility(View.VISIBLE);
                 Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
             }
         });
        }
    }
    private void updateUI(@Nullable FirebaseUser user) {

        if(user != null){

            FirebaseFirestore.getInstance().collection("User").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
                    if(userInfo != null) {
                        sharedPreference.addUser(userInfo);
                        if (userInfo.getType().equals("Admin")) {
                            Intent done = new Intent(getContext(), AdminActivity.class);
                            startActivity(done);
                        }
                        if ("User".equals(userInfo.getType())) {
                            Intent done = new Intent(getContext(), MainActivity.class);
                            startActivity(done);

                            //finish();
                        }
                        if ("Shop owner".equals(userInfo.getType())){
                            db.collection("Shop").whereEqualTo("user",userInfo).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if(queryDocumentSnapshots!=null){
                                        shopModel shop = queryDocumentSnapshots.toObjects(shopModel.class).get(0);
                                        sharedPreference.SaveShop(shop);
                                        Intent done = new Intent(getContext(), HomePageShop.class);
                                        startActivity(done);
                                    }
                                }
                            });

                        }
                        email.setText("");
                        password.setText("");
                    }

                }
            });
        }
    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
}
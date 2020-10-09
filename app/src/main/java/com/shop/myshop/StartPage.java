package com.shop.myshop;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class StartPage extends AppCompatActivity {
    //private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
      //  mAuth = FirebaseAuth.getInstance();

    }
//    private void updateUI(@Nullable FirebaseUser user) {
////
////        if(user != null){
////
////        FirebaseFirestore.getInstance().collection("User").document(user.getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
////            @Override
////            public void onSuccess(DocumentSnapshot documentSnapshot) {
////                UserInfo userInfo = documentSnapshot.toObject(UserInfo.class);
////                if(userInfo.getType().equals("Admin")){
////                    Intent done = new Intent(StartPage.this,AdminActivity.class);
////                    startActivity(done);
////                    finish();
////                }
////                if(userInfo.getType().equals("User")) {
////                    Intent done = new Intent(StartPage.this,MainActivity.class);
////                    startActivity(done);
////                    finish();
////                }
////
////            }
////        });
////        Intent done = new Intent(StartPage.this,AdminActivity.class);
////
////            startActivity(done);
////            finish();
////        }
//    }
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }
}
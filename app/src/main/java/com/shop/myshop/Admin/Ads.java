package com.shop.myshop.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.shop.myshop.AdsModel;
import com.shop.myshop.CategoryModel;
import com.shop.myshop.ProductsModel;
import com.shop.myshop.R;
import com.squareup.picasso.Picasso;

import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.EXTRA_ALLOW_MULTIPLE;

public class Ads extends Fragment {
ImageView adsImage, edit, ok;
   Uri ImageUri;
   String imageString;
   ProductsModel product;
  // Gson gson;
   FirebaseFirestore db;
   AdsModel adsModel;
    StorageReference mStorageRef;
    ProgressBar progressBar;
    public Ads() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ads, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference("Ads");
        db = FirebaseFirestore.getInstance();
        adsImage = view.findViewById(R.id.ads);
        edit = view.findViewById(R.id.edit);
        ok = view.findViewById(R.id.ok);
   //     gson = new Gson();
        progressBar = view.findViewById(R.id.progressBar4);
       product = AdsArgs.fromBundle(getArguments()).getProduct();
         haveAds(product.getID());
        adsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                UploadImage();
            }
        });

    }

    public void image(){
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(EXTRA_ALLOW_MULTIPLE, false);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && null != data){
            ImageUri = data.getData();
            adsImage.setImageURI(ImageUri);
        }
    }
    public void UploadImage(){

        final StorageReference childRef = mStorageRef.child(product.getID());
        childRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //    Toast.makeText(UploadProduct.this,"Done Upload Image",Toast.LENGTH_LONG).show();
                childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageString = String.valueOf(uri);
                        Toast.makeText(getActivity(),"done Upload Image",Toast.LENGTH_SHORT).show();
                        UploadFirebase();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),""+ e,Toast.LENGTH_LONG).show();
//                        pd.dismiss();
                    }
                });
            }
        });


    }

    private void UploadFirebase() {
        Date date = new Date();
        adsModel = new AdsModel(product.getID(),imageString);
        db.collection("Ads").document(product.getID())
                .set(adsModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                     UpdateProduct();

//
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),""+ e,Toast.LENGTH_LONG).show();

                    }

                });
    }

    private void UpdateProduct() {
        product.setHaveAds(true);
        db.collection("Products").document(product.getID()).set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(),"done",Toast.LENGTH_SHORT).show();
                Navigation.findNavController(getView()).navigateUp();
                Navigation.findNavController(getView()).navigateUp();
                Navigation.findNavController(getView()).navigateUp();
            }
        });

    }

    public void haveAds(final String id){

            db.collection("Ads").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        imageString = documentSnapshot.get("image").toString();
                        Picasso.get().load(Uri.parse(imageString)).into(adsImage);
                        ImageUri = Uri.parse(imageString);
                    }

                }
            });
        }

    }

package com.shop.myshop.User;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shop.myshop.R;
import com.shop.myshop.SharedPreference;
import com.shop.myshop.shopModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class requestPage extends Fragment {
    TextInputLayout name, type, description;
    ImageView image;
    String id;
    shopModel shop;
    Button done, addImage;
    SharedPreference sharedPreference;
    Uri ImageUri;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    private ArrayList<String> listImage = new ArrayList<String>();
    int uploadCont = 0;
    String imageEncoded;
    List<String> imagesEncodedList;
    HorizontalScrollView switchImage;
    LinearLayout imageGallery;
    StorageReference mStorageRef;
    Uri Image;
    String imageString;
    public requestPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreference = new SharedPreference(getContext());
        switchImage = view.findViewById(R.id.switchImage);
        mStorageRef = FirebaseStorage.getInstance().getReference("Shop");
        imageGallery = (LinearLayout) view.findViewById(R.id.imageGallery);
        addImage = view.findViewById(R.id.AddImage);
        name = view.findViewById(R.id.name);
        type = view.findViewById(R.id.type);
        description = view.findViewById(R.id.about);
        image = view.findViewById(R.id.imageView6);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);

            }
        });
        done = view.findViewById(R.id.done);
        id = FirebaseFirestore.getInstance().collection("Shop").document().getId();
        shop = new shopModel();
        shop.setUser(sharedPreference.getUser());
        done.setVisibility(View.INVISIBLE);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shop.setImages(listImage);
                shop.setShopState("Request");
                shop.setImage(imageString);
                shop.setId(id);
                shop.setDescription(description.getEditText().getText().toString());
                shop.setType(type.getEditText().getText().toString());
                shop.setName(name.getEditText().getText().toString());
                FirebaseFirestore.getInstance().collection("Shop").document(id).set(shop).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
                        Navigation.findNavController(getView()).navigateUp();
                    }
                });


            }
        });
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);


            }
        });



        switchImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float currentPosition = switchImage.getScrollX();
                    float pagesCount = switchImage.getChildCount();
                    float pageLengthInPx = switchImage.getMeasuredWidth() / pagesCount;

                    int page = (int) (Math.floor((currentPosition - pageLengthInPx / 2) / pageLengthInPx) + 1);
                    switchImage.scrollTo((int) (page * pageLengthInPx), 0);
                }
                return false;
            }
        });

    }
    private View getImageView(Uri image) {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(0, 0, 10, 0);
        imageView.setMinimumWidth(Resources.getSystem().getDisplayMetrics().widthPixels);
        imageView.setMinimumHeight(300);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(lp);
        //imageView.setImageResource(image);
        Picasso.get().load(image).into(imageView);
        return imageView;
    }
    public void setImage(ArrayList<Uri> image){

        for (int i=0;i<image.size();i++){

            imageGallery.addView(getImageView(image.get(i)));
            //  Picasso.get().load(images[i]).into(image);
        }
        UploadImage();


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            imageGallery.removeAllViews();
            mArrayUri.clear();
            listImage.clear();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            imagesEncodedList = new ArrayList<String>();
            if (data.getData() != null) {

                ImageUri = data.getData();
                mArrayUri.add(ImageUri);
                // Get the cursor
                Cursor cursor = getActivity().getContentResolver().query(ImageUri,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageEncoded = cursor.getString(columnIndex);
                cursor.close();

            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();

                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        // Get the cursor
                        Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded = cursor.getString(columnIndex);
                        imagesEncodedList.add(imageEncoded);
                        cursor.close();

                    }

                }
            }
          //  UploadProduct.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "selected image :" + mArrayUri.size(), Toast.LENGTH_LONG).show();
            setImage(mArrayUri);
        } else {
           if(requestCode == 2 && resultCode == RESULT_OK && null != data){
               Image = data.getData();
               image.setImageURI(Image);
               UploadProfile();

           }else {
               Toast.makeText(getActivity(), "You haven't picked Image",
                       Toast.LENGTH_LONG).show();
           }
        }
    }    public void UploadProfile(){

        final StorageReference childRef = mStorageRef.child(id);
        childRef.putFile(Image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //    Toast.makeText(UploadProduct.this,"Done Upload Image",Toast.LENGTH_LONG).show();
                childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        imageString = String.valueOf(uri);

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
    public void UploadImage(){
        for( uploadCont = 0;uploadCont<mArrayUri.size();++uploadCont){
            Uri inImage = mArrayUri.get(uploadCont);

            final StorageReference childRef = mStorageRef.child(id+uploadCont);
            childRef.putFile(inImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //    Toast.makeText(UploadProduct.this,"Done Upload Image",Toast.LENGTH_LONG).show();
                    childRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url= String.valueOf(uri);
                            listImage.add(url);
                            if(listImage.size() == mArrayUri.size()){
                                done.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
            });
        }



            }


}


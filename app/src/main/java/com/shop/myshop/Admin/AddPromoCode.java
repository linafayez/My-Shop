 package com.shop.myshop.Admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shop.myshop.PromoCodeModel;
import com.shop.myshop.R;

import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class AddPromoCode extends Fragment {
    TextInputLayout PromoCode , EndAfter, Discount;
    Button Add;
    String id;
    FirebaseFirestore db ;
    PromoCodeModel promoCodeModel;
    public AddPromoCode() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_promo_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = FirebaseFirestore.getInstance();
        id = db.collection("PromoCode").document().getId();
        PromoCode = view.findViewById(R.id.voucher);
        EndAfter = view.findViewById(R.id.EndDate);

        Discount= view.findViewById(R.id.Discount);
        Add = view.findViewById(R.id.add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Add.setVisibility(View.INVISIBLE);
               // Toast.makeText(getContext(),PromoCode.getEditText().getText()+"",Toast.LENGTH_LONG).show();
               promoCodeModel = new PromoCodeModel(id,Timestamp.now(),   new Timestamp(Timestamp.now().getSeconds()+Integer.parseInt(EndAfter.getEditText().getText().toString())*60*60*24,0),PromoCode.getEditText().getText().toString(),Integer.parseInt(Discount.getEditText().getText().toString()));
               Upload(promoCodeModel);

            }
        });
    }

    private void Upload(PromoCodeModel promoCodeModel) {
        db.collection("PromoCode").document(id).set(promoCodeModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getContext(),"Done",Toast.LENGTH_LONG).show();
                Navigation.findNavController(getView()).navigateUp();
            }
        });
    }
}
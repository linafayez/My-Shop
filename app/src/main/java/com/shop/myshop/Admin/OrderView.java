package com.shop.myshop.Admin;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shop.myshop.R;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;
import java.util.List;

public class OrderView extends Fragment {
    StepView stepView;
    public OrderView() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      stepView= view.findViewById(R.id.step_view);
        List<String> step = new ArrayList<>();
        step.add("Step 1");
        step.add("step 2");
        step.add("step 3");
        stepView.getState()
                .selectedTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
                .animationType(StepView.ANIMATION_CIRCLE)
                .selectedCircleColor(ContextCompat.getColor(getContext(), R.color.colorAccent))
             //   .selectedCircleRadius(getResources().getDimensionPixelSize(R.dimen.dp14))
                .selectedStepNumberColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))
                // You should specify only stepsNumber or steps array of strings.
                // In case you specify both steps array is chosen.
                .steps(step)
                // You should specify only steps number or steps array of strings.
                // In case you specify both steps array is chosen.
                .stepsNumber(step.size())

                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
               // .stepLineWidth(getResources().getDimensionPixelSize(R.dimen.dp1))
              //  .textSize(getResources().getDimensionPixelSize(R.dimen.sp14))
               // .stepNumberTextSize(getResources().getDimensionPixelSize(R.dimen.sp16))
                .typeface(ResourcesCompat.getFont(getContext(), R.font.roboto_light))
                // other state methods are equal to the corresponding xml attributes
                .commit();
        stepView.go(1, true);


    }
}
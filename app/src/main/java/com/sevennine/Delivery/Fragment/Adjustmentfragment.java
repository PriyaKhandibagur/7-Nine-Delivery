package com.sevennine.Delivery.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.sevennine.Delivery.R;

//Our class extending fragment
public class Adjustmentfragment extends Fragment {

    LinearLayout pick_up_arrow,back_feed,reached_loc;
    Fragment selectedFragment;
    TextView customer_address,customer_name,personal_details,bank_details;
    public static Adjustmentfragment newInstance() {
        Adjustmentfragment itemOnFragment = new Adjustmentfragment();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.adjustment_layout, container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));


        return view;
        
    }



}

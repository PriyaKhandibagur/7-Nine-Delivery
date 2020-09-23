package com.sevennine.Delivery.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevennine.Delivery.R;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

//Our class extending fragment
public class InsentivesFragment extends Fragment {

    LinearLayout pick_up_arrow,back_feed,reached_loc;
    Fragment selectedFragment;
    TextView customer_address,customer_name,personal_details,bank_details;
    public static InsentivesFragment newInstance() {
        InsentivesFragment itemOnFragment = new InsentivesFragment();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.incentives_lay, container, false);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));


        return view;
        
    }



}

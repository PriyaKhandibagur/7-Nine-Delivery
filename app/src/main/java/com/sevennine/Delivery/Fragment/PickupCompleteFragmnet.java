package com.sevennine.Delivery.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.sevennine.Delivery.Adapter.PickupItemsAdapter;
import com.sevennine.Delivery.Bean.PickupItemsBean;
import com.sevennine.Delivery.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Our class extending fragment
public class PickupCompleteFragmnet extends Fragment {
    public static PickupItemsAdapter madapter;
    LinearLayout pick_up_arrow,proceed_conf,pick_up_complete;
    Fragment selectedFragment;
    RecyclerView recyclerView;
    private BottomSheetBehavior mBottomSheetBehavior1;
    LinearLayout linearLayout,back_feed;
    RatingBar ratingBar;
    String rateValue;
    TextView customer_address,customer_name,submit;
    public static PickupCompleteFragmnet newInstance() {
        PickupCompleteFragmnet itemOnFragment = new PickupCompleteFragmnet();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_rating_layout, container, false);
        customer_address=view.findViewById(R.id.customer_address);
        customer_name=view.findViewById(R.id.customer_name);
        pick_up_arrow=view.findViewById(R.id.delivery_arrow);
        pick_up_complete=view.findViewById(R.id.pick_up_complete);
        ratingBar=view.findViewById(R.id.ratingBar);
        submit=view.findViewById(R.id.submit);
        linearLayout=view.findViewById(R.id.bottom_sheet1);
        back_feed=view.findViewById(R.id.back_feed);
        // sessionManager = new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("ONBACK", "keyCodezzzzzzzzzq  : " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("ONBACK", "onKey Back listener is working!!!");
                   /* selectedFragment = AddressDetailFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("dhskswa");
                    transaction.commit();*/
                    FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                    return true;
                }
                return false;
            }
        });
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        mBottomSheetBehavior1 = BottomSheetBehavior.from(linearLayout);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);

        pick_up_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rateValue!=null){
                    Bundle bundle=new Bundle();
                    bundle.putString("delivery_map_status","true");
                    selectedFragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    selectedFragment.setArguments(bundle);
                    transaction.commit();
                }else{
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);

// set the peek height
                    mBottomSheetBehavior1.setPeekHeight(1950);

// set hideable or not
                    mBottomSheetBehavior1.setHideable(false);

// set callback for changes

                    mBottomSheetBehavior1.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {

                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                        }
                    });

                }
            }
        });
        pick_up_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rateValue!=null){
                    Bundle bundle=new Bundle();
                    bundle.putString("delivery_map_status","true");
                    selectedFragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    selectedFragment.setArguments(bundle);
                    transaction.commit();
                }else{
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);

// set the peek height
                    mBottomSheetBehavior1.setPeekHeight(1950);

// set hideable or not
                    mBottomSheetBehavior1.setHideable(false);

// set callback for changes

                    mBottomSheetBehavior1.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                        @Override
                        public void onStateChanged(@NonNull View bottomSheet, int newState) {

                        }

                        @Override
                        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

                        }
                    });

                }


               /* selectedFragment = PickupCompleteFragmnet.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();*/
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                rateValue = String.valueOf(ratingBar.getRating());
                System.out.println("Rate for Module is"+rateValue);
                submit.setBackgroundResource(R.drawable.border_blue_filled_curve);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rateValue!=null){
                    mBottomSheetBehavior1.setHideable(true);
                    mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);

                }
            }
        });
        return view;


        
    }



}

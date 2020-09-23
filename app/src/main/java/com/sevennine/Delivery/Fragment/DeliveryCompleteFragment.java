package com.sevennine.Delivery.Fragment;

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
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//Our class extending fragment
public class DeliveryCompleteFragment extends Fragment {

    LinearLayout reached,back_feed;
    Fragment selectedFragment;
    private BottomSheetBehavior mBottomSheetBehavior1;
    LinearLayout linearLayout;
    RatingBar ratingBar;
    String rateValue;
    SessionManager sessionManager;
    TextView customer_address,customer_name,store_name,store_address;
    TextView delivery_complete,submit;
    public static DeliveryCompleteFragment newInstance() {
        DeliveryCompleteFragment itemOnFragment = new DeliveryCompleteFragment();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottomsheet_delivery_rating_layout, container, false);
        customer_address=view.findViewById(R.id.customer_address);
        customer_name=view.findViewById(R.id.customer_name);
        store_name=view.findViewById(R.id.store_name);
        store_address=view.findViewById(R.id.store_address);
        reached=view.findViewById(R.id.reached);
        delivery_complete=view.findViewById(R.id.delivery_complete);
        ratingBar=view.findViewById(R.id.ratingBar);
        submit=view.findViewById(R.id.submit);
        back_feed=view.findViewById(R.id.back_feed);
        linearLayout=view.findViewById(R.id.bottom_sheet1);
         sessionManager = new SessionManager(getActivity());
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
        customer_name.setText(sessionManager.getRegId("custname"));
        customer_address.setText(sessionManager.getRegId("custaddress"));
        store_name.setText(sessionManager.getRegId("storename"));
        store_address.setText(sessionManager.getRegId("storeaddress"));
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        delivery_complete.setText("DELIVERY COMPLETE");

        mBottomSheetBehavior1 = BottomSheetBehavior.from(linearLayout);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);
        reached.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rateValue!=null){
                    selectedFragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
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

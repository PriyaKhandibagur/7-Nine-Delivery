package com.sevennine.Delivery.Fragment;

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
import com.sevennine.Delivery.SessionManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//Our class extending fragment
public class PaymentSuccessfullFragment extends Fragment {

    LinearLayout got_it,back_feed;
    Fragment selectedFragment;
    TextView  customer_address,customer_name,custaddress;
    SessionManager sessionManager;
    public static PaymentSuccessfullFragment newInstance() {
        PaymentSuccessfullFragment itemOnFragment = new PaymentSuccessfullFragment();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_successfull_lay, container, false);
        customer_address=view.findViewById(R.id.customer_address);
        customer_name=view.findViewById(R.id.cust_name);
        custaddress=view.findViewById(R.id.cust_address);
        back_feed=view.findViewById(R.id.back_feed);
        got_it=view.findViewById(R.id.got_it);
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
        sessionManager=new SessionManager(getActivity());

        custaddress.setText(sessionManager.getRegId("custaddress"));
        customer_name.setText(sessionManager.getRegId("custname"));
        customer_address.setText(sessionManager.getRegId("custaddress"));
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        got_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = DeliveryCompleteFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("dhskswakdhj");
                transaction.commit();
            }
        });
        return view;
        
    }



}

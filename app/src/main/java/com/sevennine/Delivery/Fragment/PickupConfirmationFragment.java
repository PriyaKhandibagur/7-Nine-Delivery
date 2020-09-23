package com.sevennine.Delivery.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevennine.Delivery.Adapter.OrdersListAdapter;
import com.sevennine.Delivery.Adapter.PickupItemsAdapter;
import com.sevennine.Delivery.Bean.NewOrderBean;
import com.sevennine.Delivery.Bean.PickupItemsBean;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Our class extending fragment
public class PickupConfirmationFragment extends Fragment {
    public static PickupItemsAdapter madapter;
    public static List<PickupItemsBean> newOrderBeansList = new ArrayList<>();
    LinearLayout pick_up_arrow,proceed_conf,back_feed;
    Fragment selectedFragment;
    RecyclerView recyclerView;
    SessionManager sessionManager;
    TextView customer_address,customer_name,custaddress,payment_amt;
    public static PickupConfirmationFragment newInstance() {
        PickupConfirmationFragment itemOnFragment = new PickupConfirmationFragment();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pickup_confirmation, container, false);
        customer_address=view.findViewById(R.id.customer_address);
        customer_name=view.findViewById(R.id.customer_name);
        pick_up_arrow=view.findViewById(R.id.delivery_arrow);
        recyclerView=view.findViewById(R.id.items_recy);
        proceed_conf=view.findViewById(R.id.proceed_conf);
        back_feed=view.findViewById(R.id.back_feed);
        payment_amt=view.findViewById(R.id.payment_amt);
        custaddress=view.findViewById(R.id.cust_address);
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
        sessionManager=new SessionManager(getActivity());
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });
        custaddress.setText(sessionManager.getRegId("custaddress"));
        customer_name.setText(sessionManager.getRegId("custname"));
        customer_address.setText(sessionManager.getRegId("custaddress"));
        payment_amt.setText("Pay ₹"+sessionManager.getRegId("totalamount")+" to Store");
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        PickupItemsBean bean=new PickupItemsBean("1","Fortune Refined Oil","5 Ltr");
        newOrderBeansList.add(bean);
        newOrderBeansList.add(bean);
        madapter=new PickupItemsAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);

        proceed_conf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.pick_confirm_popup);

                TextView confirm = (TextView) dialog.findViewById(R.id.confirm);
                TextView missing = (TextView) dialog.findViewById(R.id.missing);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        selectedFragment = PickupCompleteFragmnet.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.addToBackStack("dhskswaddw");
                        transaction.commit();

                    }
                });
                missing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });
        pick_up_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = PickupLocationMapFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.commit();
            }
        });
        return view;
        
    }



}

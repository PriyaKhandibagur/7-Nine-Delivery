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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

//Our class extending fragment
public class AddressDetailAfterPickupFragment extends Fragment {

    LinearLayout pick_up_arrow,back_feed,reached_loc;
    Fragment selectedFragment;
    SessionManager sessionManager;
    TextView customer_address,customer_name,store_name,store_address,pickupid,cust_name_title;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mProfileRef = firebaseDatabase.getReference();
    String userId;
    public static AddressDetailAfterPickupFragment newInstance() {
        AddressDetailAfterPickupFragment itemOnFragment = new AddressDetailAfterPickupFragment();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_detail_after_pick_up, container, false);
        customer_address=view.findViewById(R.id.customer_address);
        customer_name=view.findViewById(R.id.customer_name);
        pick_up_arrow=view.findViewById(R.id.pick_up_arrow);
        store_name=view.findViewById(R.id.store_name);
        store_address=view.findViewById(R.id.store_address);
        cust_name_title=view.findViewById(R.id.custname_title);
        pickupid=view.findViewById(R.id.pickupid);
        pick_up_arrow=view.findViewById(R.id.delivery_arrow);
        back_feed=view.findViewById(R.id.back_feed);
        reached_loc=view.findViewById(R.id.reached_loc);
        // sessionManager = new SessionManager(getActivity());
        sessionManager=new SessionManager(getActivity());
        userId = sessionManager.getRegId("userId");

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
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStack ("asdfg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack ("asdfg", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        customer_name.setText(sessionManager.getRegId("custname"));
        customer_address.setText(sessionManager.getRegId("custaddress"));
        store_name.setText(sessionManager.getRegId("storename"));
        store_address.setText(sessionManager.getRegId("storeaddress"));
        cust_name_title.setText(sessionManager.getRegId("custname"));
        pickupid.setText(" - "+sessionManager.getRegId("pickupid"));
        reached_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProfileRef.child(userId).child("Order Summary").child(sessionManager.getRegId("orderid")).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Boolean status = true;
                        dataSnapshot.getRef().child("reachedPickupLocation").setValue(status);
                        //   Toast.makeText(DirectionsRouteMapActivity.this ,"Location saved to the Firebasedatabase",Toast.LENGTH_LONG).show();

                        // dialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                selectedFragment = PickupConfirmationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("dhskswadwu");
                transaction.commit();
            }
        });
        pick_up_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = PickupConfirmationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("dhskswadwu");
                transaction.commit();
            }
        });
        return view;
        
    }



}

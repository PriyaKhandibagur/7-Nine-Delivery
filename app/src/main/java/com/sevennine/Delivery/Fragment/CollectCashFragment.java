package com.sevennine.Delivery.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
public class CollectCashFragment extends Fragment {

    LinearLayout collect_cash,back_feed;
    Fragment selectedFragment;
    TextView customer_address,customer_name,custaddress,collect_cash_title,amount;
    SessionManager sessionManager;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mProfileRef = firebaseDatabase.getReference();
    String userId;
    public static CollectCashFragment newInstance() {
        CollectCashFragment itemOnFragment = new CollectCashFragment();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.collect_cash_lay, container, false);
        customer_address=view.findViewById(R.id.customer_address);
        customer_name=view.findViewById(R.id.customer_name);
        collect_cash_title=view.findViewById(R.id.collect_cash_title);
        amount=view.findViewById(R.id.amount);
        custaddress=view.findViewById(R.id.cust_address);
        collect_cash=view.findViewById(R.id.collect_cash);
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
        userId = sessionManager.getRegId("userId");

        custaddress.setText(sessionManager.getRegId("custaddress"));
        customer_name.setText(sessionManager.getRegId("custname"));
        customer_address.setText(sessionManager.getRegId("custaddress"));
        collect_cash_title.setText("Collect ₹"+sessionManager.getRegId("totalamount")+" from customer");
        amount.setText(sessionManager.getRegId("totalamount"));
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                fm.popBackStack();
            }
        });

        collect_cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.collect_cash_popup);
                final TextView reenter_amount=dialog.findViewById(R.id.reenter_amount);
                final LinearLayout call_instuction=dialog.findViewById(R.id.call_instuction);
                final TextView verify_text=dialog.findViewById(R.id.verify_text);
                final TextView amount=dialog.findViewById(R.id.amount);
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                Button collect_cash = (Button) dialog.findViewById(R.id.collect_cash);
              //  TextView missing = (TextView) dialog.findViewById(R.id.missing);

                collect_cash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(amount.getText().toString().equals(sessionManager.getRegId("totalamount")))){
                            call_instuction.setVisibility(View.VISIBLE);
                            verify_text.setText("Enter correct amount");
                        }else if (!(reenter_amount.getText().toString().equals(amount.getText().toString()))){
                            call_instuction.setVisibility(View.VISIBLE);
                            verify_text.setText("Re-Entered amount is not matching with actual amount");
                        }else{
                            call_instuction.setVisibility(View.GONE);
                            dialog.dismiss();
                            mProfileRef.child(userId).child("Order Summary").child(sessionManager.getRegId("orderid")).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String collectedcash = amount.getText().toString();
                                    dataSnapshot.getRef().child("collectedCash").setValue(collectedcash);
                                    //   Toast.makeText(DirectionsRouteMapActivity.this ,"Location saved to the Firebasedatabase",Toast.LENGTH_LONG).show();

                                    // dialog.dismiss();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            selectedFragment = PaymentSuccessfullFragment.newInstance();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame_layout1, selectedFragment);
                            transaction.addToBackStack("dhskswfadkw");
                            transaction.commit();
                        }


                    }
                });
               dialog.show();
            }
        });
        return view;
        
    }



}

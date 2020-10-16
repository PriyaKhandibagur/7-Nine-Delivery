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
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.sevennine.Delivery.Bean.AccountDetails;
import com.sevennine.Delivery.Bean.User;
import com.sevennine.Delivery.R;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import static com.sevennine.Delivery.general.FirebaseConstants.TABLE_SAVINGS_ACCOUNT;
import static com.sevennine.Delivery.general.FirebaseConstants.TABLE_USERS;


//Our class extending fragment
public class BankDetails extends Fragment {

    LinearLayout got_it,back_feed;
    Fragment selectedFragment;
    TextView customer_address,customer_name,name,acc_no,branch,ifsc_code,pan_card;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseStorage mFirebaseStorage;
    private FirebaseUser mActiveUser;
    public static BankDetails newInstance() {
        BankDetails itemOnFragment = new BankDetails();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bank_details, container, false);
        customer_address=view.findViewById(R.id.customer_address);
        customer_name=view.findViewById(R.id.customer_name);
        back_feed=view.findViewById(R.id.back_feed);
        name=view.findViewById(R.id.aname);
        acc_no=view.findViewById(R.id.acc_no);
        branch=view.findViewById(R.id.branch);
        ifsc_code=view.findViewById(R.id.ifsc);
        pan_card=view.findViewById(R.id.pan_no);
        got_it=view.findViewById(R.id.got_it);
        // sessionManager = new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));

        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = ProfileFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("dhsksw");
                transaction.commit();
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("ONBACK", "keyCodezzzzzzzzzq  : " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("ONBACK", "onKey Back listener is working!!!");
                    selectedFragment = ProfileFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("dhsksw");
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();
        mActiveUser = mFirebaseAuth.getCurrentUser();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();

        DatabaseReference databaseReference = mFirebaseDatabase.getReference(mFirebaseAuth.getUid());

      /*  AccountDetails accountDetails = new AccountDetails("Priyanka","567809876524","HDFC","HDFC0124365","DYCHB9898A");

        mDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).child(TABLE_SAVINGS_ACCOUNT).setValue(accountDetails);

        Toast.makeText(getActivity(), "Personal details updated successfully", Toast.LENGTH_SHORT).show();*/
        if(mFirebaseAuth.getCurrentUser()!=null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    AccountDetails accountDetails = dataSnapshot.child(TABLE_SAVINGS_ACCOUNT).getValue(AccountDetails.class);

                    name.setText(accountDetails.getAccholderName());
                    acc_no.setText(accountDetails.getAccNumber());
                    branch.setText(accountDetails.getBankname());
                    ifsc_code.setText(accountDetails.getIfscCode());
                    pan_card.setText(accountDetails.getPanNumber());
                    // mTextEmail.setText(mActiveUser.getEmail());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // onError(databaseError.getMessage());
                    Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }else{

        }




        return view;
        
    }



}

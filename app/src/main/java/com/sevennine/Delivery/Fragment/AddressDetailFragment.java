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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sevennine.Delivery.Adapter.OrdersListAdapter;
import com.sevennine.Delivery.Bean.DutyStatusBean;
import com.sevennine.Delivery.Bean.NewOrderBean;
import com.sevennine.Delivery.Person;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;
import com.sevennine.Delivery.Urls;
import com.sevennine.Delivery.Volly_class.Crop_Post;
import com.sevennine.Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//Our class extending fragment
public class AddressDetailFragment extends Fragment {

    LinearLayout pick_up_arrow;
    Fragment selectedFragment;
    SessionManager sessionManager;
    TextView customer_address,customer_name,store_name,store_address,pickupid,cust_name_title;
    String userId;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mProfileRef = firebaseDatabase.getReference();

    public static AddressDetailFragment newInstance() {
        AddressDetailFragment itemOnFragment = new AddressDetailFragment();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.address_detail_item, container, false);
        customer_address=view.findViewById(R.id.customer_address);
        customer_name=view.findViewById(R.id.customer_name);
        pick_up_arrow=view.findViewById(R.id.pick_up_arrow);
        store_name=view.findViewById(R.id.store_name);
        store_address=view.findViewById(R.id.store_address);
        cust_name_title=view.findViewById(R.id.custname_title);
        pickupid=view.findViewById(R.id.pickupid);
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
                    selectedFragment = OrdersFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("dhskswa");
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });
        sessionManager=new SessionManager(getActivity());
        System.out.println("orderidddd "+sessionManager.getRegId("orderid"));

        if (getArguments()!=null) {
            customer_name.setText(getArguments().getString("customer_name1"));
            customer_address.setText(getArguments().getString("customer_address1"));
            store_name.setText(sessionManager.getRegId("storename"));
            store_address.setText(sessionManager.getRegId("storeaddress"));
            cust_name_title.setText(sessionManager.getRegId("custname"));
            pickupid.setText(" - "+sessionManager.getRegId("pickupid"));
        }
        userId = sessionManager.getRegId("userId");
        System.out.println("LKJH "+userId);
//.equalTo(sessionManager.getRegId("orderid"))
       /* mProfileRef.child(userId).child("Order Summary").child(sessionManager.getRegId("orderid")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("Orderrrrr "+dataSnapshot);
                System.out.println("Orderrrrr 1 "+dataSnapshot.getValue(Person.class).getCustlatlang().get("latitude"));
                System.out.println("Orderrrrr 2 "+dataSnapshot.getChildren());
                System.out.println("Orderrrrr 4 "+dataSnapshot.getChildrenCount());

               // Person order = new Person(dataSnapshot.getValue());
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Person news = postSnapshot.getValue(Person.class);
//                    System.out.println("Orderrrrr 3 "+news.getCustomername());
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Orderrrrr err "+databaseError);
            }

            // ...
        });*/
        pick_up_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = PickupLocationMapFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("khdsjkj");
                transaction.commit();
            }
        });
        return view;
        
    }



}

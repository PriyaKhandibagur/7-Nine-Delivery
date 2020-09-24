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

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sevennine.Delivery.Adapter.LoginHistoryAdapter;
import com.sevennine.Delivery.Adapter.RewardsAdapter;
import com.sevennine.Delivery.Bean.Weeklybean3;
import com.sevennine.Delivery.R;

import java.util.ArrayList;
import java.util.List;

//Our class extending fragment
public class LoginHistory extends Fragment {

    public static LoginHistoryAdapter madapter3;

    public static List<Weeklybean3> newOrderBeansList3= new ArrayList<>();
    LinearLayout back_feed,today;
    Fragment selectedFragment;
    RecyclerView recyclerView3;

    public static LoginHistory newInstance() {
        LoginHistory itemOnFragment = new LoginHistory();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loginhistory, container, false);


        recyclerView3=view.findViewById(R.id.lrecy);
        back_feed=view.findViewById(R.id.back_feed);
        today=view.findViewById(R.id.linearLayout1);
        // sessionManager = new SessionManager(getActivity());
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFragment = HomeFragment.newInstance();
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
                    selectedFragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("dhsksw");
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });



/////////////////////////////

        newOrderBeansList3.clear();
        GridLayoutManager mLayoutManager_farm3 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(mLayoutManager_farm3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        Weeklybean3 weeklybean=new Weeklybean3("[30 Apr - 5 Sep]","This Week");
        newOrderBeansList3.add(weeklybean);

        Weeklybean3 weeklybean1=new Weeklybean3("[23 Apr - 29 Apr]","Last Week");
        newOrderBeansList3.add(weeklybean1);


        Weeklybean3 weeklybean2=new Weeklybean3("[16 Apr - 22 Apr]","Week 15");
        newOrderBeansList3.add(weeklybean2);

      Weeklybean3 weeklybean3=new Weeklybean3("[9 Apr - 15 Apr]","Week 14");
        newOrderBeansList3.add(weeklybean3);


        Weeklybean3 weeklybean4=new Weeklybean3("[2 Apr - 8 Apr]","Week 13");
        newOrderBeansList3.add(weeklybean4);




        madapter3=new LoginHistoryAdapter(getActivity(),newOrderBeansList3);
        recyclerView3.setAdapter(madapter3);





        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = WeeklyloginHistory.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();
            }
        });
        return view;
        
    }



}

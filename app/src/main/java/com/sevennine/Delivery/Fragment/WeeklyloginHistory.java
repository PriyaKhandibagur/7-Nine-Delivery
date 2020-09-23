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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sevennine.Delivery.Adapter.LoginHistoryAdapter;
import com.sevennine.Delivery.Adapter.WeeklyLoginAdapter;
import com.sevennine.Delivery.Bean.Weeklybean3;
import com.sevennine.Delivery.R;

import java.util.ArrayList;
import java.util.List;

//Our class extending fragment
public class WeeklyloginHistory extends Fragment {

    public static WeeklyLoginAdapter madapter3;

    public static List<Weeklybean3> newOrderBeansList3= new ArrayList<>();
    LinearLayout back_feed;
    Fragment selectedFragment;
    RecyclerView recyclerView3;

    public static WeeklyloginHistory newInstance() {
        WeeklyloginHistory itemOnFragment = new WeeklyloginHistory();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weekly_logindetails, container, false);


        recyclerView3=view.findViewById(R.id.wrecy);
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



/////////////////////////////

        newOrderBeansList3.clear();
        GridLayoutManager mLayoutManager_farm3 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(mLayoutManager_farm3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        Weeklybean3 weeklybean=new Weeklybean3("8hr","Friday,4 Sep");
        newOrderBeansList3.add(weeklybean);

        Weeklybean3 weeklybean1=new Weeklybean3("9hr 45m","Thursday,3 Sep");
        newOrderBeansList3.add(weeklybean1);


        Weeklybean3 weeklybean2=new Weeklybean3("7hr 20m","Wednesday,2 Sep");
        newOrderBeansList3.add(weeklybean2);

      Weeklybean3 weeklybean3=new Weeklybean3("10hr 38m","Tuesday, 1 Sep");
        newOrderBeansList3.add(weeklybean3);






        madapter3=new WeeklyLoginAdapter(getActivity(),newOrderBeansList3);
        recyclerView3.setAdapter(madapter3);





       /* pick_up_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = PickupLocationMapFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.commit();
            }
        });*/
        return view;
        
    }



}

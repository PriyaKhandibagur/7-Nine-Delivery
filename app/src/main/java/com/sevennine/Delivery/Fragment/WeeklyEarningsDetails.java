package com.sevennine.Delivery.Fragment;

import android.app.Dialog;
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

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sevennine.Delivery.Adapter.PickupItemsAdapter;
import com.sevennine.Delivery.Adapter.WeeklyAdapter;
import com.sevennine.Delivery.Adapter.WeeklyAdapterRecyc3;
import com.sevennine.Delivery.Adapter.WeeklyAdapterRecycle2;
import com.sevennine.Delivery.Bean.PickupItemsBean;
import com.sevennine.Delivery.Bean.Weeklybean;
import com.sevennine.Delivery.Bean.Weeklybean2;
import com.sevennine.Delivery.Bean.Weeklybean3;
import com.sevennine.Delivery.R;

import java.util.ArrayList;
import java.util.List;

//Our class extending fragment
public class WeeklyEarningsDetails extends Fragment {
    public static WeeklyAdapter madapter;
    public static WeeklyAdapterRecyc3 madapter3;
    public static List<Weeklybean> newOrderBeansList = new ArrayList<>();

    public static List<Weeklybean3> newOrderBeansList3= new ArrayList<>();
    LinearLayout back_feed;
    Fragment selectedFragment;
    public static RecyclerView recyclerView,recyclerView2,recyclerView3;

    public static WeeklyEarningsDetails newInstance() {
        WeeklyEarningsDetails itemOnFragment = new WeeklyEarningsDetails();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weekly_earning, container, false);

        recyclerView=view.findViewById(R.id.weekly_recy);
        recyclerView3=view.findViewById(R.id.weekly_recy3);
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
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        Weeklybean bean=new Weeklybean("₹710","Total Earnings","1");
        newOrderBeansList.add(bean);

        Weeklybean bean1=new Weeklybean("₹490","Order Earnings","2");
        newOrderBeansList.add(bean1);


        Weeklybean bean2=new Weeklybean("₹220","Incentives","3");
        newOrderBeansList.add(bean2);

        Weeklybean bean3=new Weeklybean("₹30","Adjustments","4");
        newOrderBeansList.add(bean3);

        madapter=new WeeklyAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);

////////////////////////////recycle2//////////////////////////






       /* Weeklybean3 weeklybean3=new Weeklybean3("₹30","Adjustments");
        newOrderBeansList3.add(weeklybean3);*/

      /*  madapter2=new WeeklyAdapterRecycle2(getActivity(),newOrderBeansList2);
        recyclerView2.setAdapter(madapter2);
*/

























/////////////////////////////

        newOrderBeansList3.clear();
        GridLayoutManager mLayoutManager_farm3 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView3.setLayoutManager(mLayoutManager_farm3);
        recyclerView3.setItemAnimator(new DefaultItemAnimator());

        Weeklybean3 weeklybean=new Weeklybean3("₹510","Thu,27 Apr(4)");
        newOrderBeansList3.add(weeklybean);

        Weeklybean3 weeklybean1=new Weeklybean3("₹190","Wed,26 Apr(2)");
        newOrderBeansList3.add(weeklybean1);


        Weeklybean3 weeklybean2=new Weeklybean3("₹685","Tue,25 Apr(5)");
        newOrderBeansList3.add(weeklybean2);

       /* Weeklybean3 weeklybean3=new Weeklybean3("₹30","Adjustments");
        newOrderBeansList3.add(weeklybean3);*/

        madapter3=new WeeklyAdapterRecyc3(getActivity(),newOrderBeansList3);
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

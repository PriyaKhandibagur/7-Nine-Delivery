package com.sevennine.Delivery.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevennine.Delivery.Adapter.WeeklyAdapterRecycle2;
import com.sevennine.Delivery.Bean.Weeklybean2;
import com.sevennine.Delivery.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//Our class extending fragment
public class EarningsFragment extends Fragment {

    LinearLayout pick_up_arrow,back_feed,reached_loc;
    Fragment selectedFragment;
    public static List<Weeklybean2> newOrderBeansList2 = new ArrayList<>();
    public static List<Weeklybean2> newOrderBeansList_next = new ArrayList<>();
    public static WeeklyAdapterRecycle2 madapter2;
    public static RecyclerView recyclerView2;
    public static EarningsFragment newInstance() {
        EarningsFragment itemOnFragment = new EarningsFragment();
        return itemOnFragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.earnings_lay, container, false);
        recyclerView2=view.findViewById(R.id.weekly_recy2);

        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark));
        newOrderBeansList2.clear();
        GridLayoutManager mLayoutManager_farm2 = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(mLayoutManager_farm2);
        recyclerView2.setItemAnimator(new DefaultItemAnimator());

        Weeklybean2 weeklybean5=new Weeklybean2("₹98","Silkboard","6.30PM","Delivered");
        newOrderBeansList2.add(weeklybean5);

        Weeklybean2 weeklybean6=new Weeklybean2("₹53","jaynagr","6.30PM","Delivered");
        newOrderBeansList2.add(weeklybean6);


        Weeklybean2 weeklybean7=new Weeklybean2("₹65","Silkboard","6.30PM","Delivered");
        newOrderBeansList2.add(weeklybean7);

        Weeklybean2 weeklybean8=new Weeklybean2("₹98","Silkboard","6.30PM","Delivered");
        newOrderBeansList_next.add(weeklybean5);

        Weeklybean2 weeklybean9=new Weeklybean2("₹53","jaynagr","6.30PM","Delivered");
        newOrderBeansList_next.add(weeklybean6);

        if (getArguments()!=null){
            newOrderBeansList_next.clear();
            madapter2=new WeeklyAdapterRecycle2(getActivity(),newOrderBeansList2);
            recyclerView2.setAdapter(madapter2);
        }else{
            newOrderBeansList2.clear();
            madapter2=new WeeklyAdapterRecycle2(getActivity(),newOrderBeansList_next);
            recyclerView2.setAdapter(madapter2);
        }


        return view;
        
    }



}

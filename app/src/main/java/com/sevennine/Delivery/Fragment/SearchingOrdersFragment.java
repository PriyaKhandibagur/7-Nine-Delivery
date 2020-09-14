package com.sevennine.Delivery.Fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.agrawalsuneet.dotsloader.loaders.CircularDotsLoader;
import com.sevennine.Delivery.Adapter.OrderAdapter;
import com.sevennine.Delivery.Bean.NewOrderBean;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.Urls;
import com.sevennine.Delivery.Volly_class.Crop_Post;
import com.sevennine.Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONObject;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SearchingOrdersFragment extends Fragment {
    ProgressBar searching;
    Fragment selectedFragment;
    public static SearchingOrdersFragment newInstance() {
        SearchingOrdersFragment fragment = new SearchingOrdersFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searching_orders_layout, container, false);
      //  searching=view.findViewById(R.id.searching_bar);
       /* Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));*/

       /* CircularDotsLoader loader = new CircularDotsLoader(getActivity());
        loader.setDefaultColor(ContextCompat.getColor(getActivity(),R.color.white));
        loader.setSelectedColor(ContextCompat.getColor(getActivity(),R.color.orange));
        loader.setBigCircleRadius(80);
        loader.setRadius(24);
        loader.setAnimDur(300);
        loader.setShowRunningShadow(true);
        loader.setFirstShadowColor(ContextCompat.getColor(getActivity(), R.color.grey));
        loader.setSecondShadowColor(ContextCompat.getColor(getActivity(), R.color.orange));*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                selectedFragment = OrdersFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.commit();
            }
        }, 3000);


                return view;
    }




}

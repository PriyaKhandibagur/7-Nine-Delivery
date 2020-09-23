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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.sevennine.Delivery.R;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class HomeLandingFragment extends Fragment {
    boolean doubleBackToExitPressedOnce = false;
    Fragment selectedFragment;
    TextView start_duty;
    public static HomeLandingFragment newInstance() {
        HomeLandingFragment fragment = new HomeLandingFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_landing_page, container, false);
        start_duty=view.findViewById(R.id.start_duty);
        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (doubleBackToExitPressedOnce) {

                        Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        intent1.addCategory(Intent.CATEGORY_HOME);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent1);
                        getActivity().finish();                   }

                    doubleBackToExitPressedOnce = true;
                    Toast toast = Toast.makeText(getActivity(),"Please click back again to exit", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER,0,0);
                    toast.show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 3000);

                }

                return true;
            }
        });

      /*  if (HomeFragment.switch_on!=null){


        }*/
        start_duty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = SearchingOrdersFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.searching_frame, selectedFragment);
                transaction.commit();
            }
        });

                return view;
    }




}

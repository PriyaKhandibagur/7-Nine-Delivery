package com.sevennine.Delivery.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.sevennine.Delivery.Bean.PickupItemsBean;
import com.sevennine.Delivery.Bean.Weeklybean;
import com.sevennine.Delivery.Fragment.Adjustmentfragment;
import com.sevennine.Delivery.Fragment.EarningsFragment;
import com.sevennine.Delivery.Fragment.HomeFragment;
import com.sevennine.Delivery.Fragment.InsentivesFragment;
import com.sevennine.Delivery.Fragment.WeeklyEarningsDetails;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;


public class WeeklyAdapter extends RecyclerView.Adapter<WeeklyAdapter.MyViewHolder>  {
    private List<Weeklybean> productList;
    Activity activity;
    Fragment selectedFragment;
    SessionManager sessionManager;
    public LinearLayout linearLayout;


    public static CardView cardView;
    String dtStart;
    Date date;
    public WeeklyAdapter(Activity activity, List<Weeklybean> productList) {
        this.productList = productList;
        this.activity=activity;
        sessionManager = new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount,text;
        public LinearLayout layout;



        public MyViewHolder(View view) {
            super(view);
            text=view.findViewById(R.id.text_total);
            amount=view.findViewById(R.id.amount);
            layout=view.findViewById(R.id.linearLayout);


        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weekly_adapter, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    final Weeklybean products = productList.get(position);
   holder.text.setText(products.getEarnings());
   holder.amount.setText(products.getAmount());

        Bundle bundle=new Bundle();
        bundle.putString("TotalEarnings","Total");
        selectedFragment = EarningsFragment.newInstance();
        FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.earnings, selectedFragment);
        selectedFragment.setArguments(bundle);
        transaction.commit();


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.getId();
                holder.layout.setBackgroundResource(R.drawable.border_empty_curve);
                holder.text.setTextColor(Color.parseColor("#000000"));
                holder.amount.setTextColor(Color.parseColor("#000000"));
                switch (products.getId()){
                    case "1":
                        Bundle bundle=new Bundle();
                        bundle.putString("TotalEarnings","Total");
                        selectedFragment = EarningsFragment.newInstance();
                        FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.earnings, selectedFragment);
                        selectedFragment.setArguments(bundle);
                        transaction.commit();
                      /*  WeeklyEarningsDetails.recyclerView2.setVisibility(View.VISIBLE);

                        WeeklyEarningsDetails.madapter2=new WeeklyAdapterRecycle2(activity,WeeklyEarningsDetails.newOrderBeansList2);
                        WeeklyEarningsDetails.recyclerView2.setAdapter(WeeklyEarningsDetails.madapter2);*/
                        break;
                    case "2":
                        selectedFragment = EarningsFragment.newInstance();
                        FragmentTransaction transaction1= ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                        transaction1.replace(R.id.earnings, selectedFragment);
                        transaction1.commit();
                       /* WeeklyEarningsDetails.recyclerView2.setVisibility(View.VISIBLE);

                        WeeklyEarningsDetails.madapter2=new WeeklyAdapterRecycle2(activity,WeeklyEarningsDetails.newOrderBeansList_next);
                        WeeklyEarningsDetails.recyclerView2.setAdapter(WeeklyEarningsDetails.madapter2);*/
                        break;
                    case "3":
                       // WeeklyEarningsDetails.recyclerView2.setVisibility(View.GONE);
                        selectedFragment = InsentivesFragment.newInstance();
                        FragmentTransaction transaction3 = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                        transaction3.replace(R.id.earnings, selectedFragment);
                        transaction3.commit();
                        break;
                    case "4":
                        selectedFragment = Adjustmentfragment.newInstance();
                        FragmentTransaction transaction4 = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                        transaction4.replace(R.id.earnings, selectedFragment);
                        transaction4.commit();
                        break;
                    default:
                }
                /*if (products.getId().equals("1")){
                    holder.layout.setBackgroundResource(R.drawable.border_empty_curve);
                    holder.text.setTextColor(Color.parseColor("#000000"));
                    holder.amount.setTextColor(Color.parseColor("#000000"));
                    WeeklyEarningsDetails.madapter2=new WeeklyAdapterRecycle2(activity,WeeklyEarningsDetails.newOrderBeansList2);
                    WeeklyEarningsDetails.recyclerView2.setAdapter(WeeklyEarningsDetails.madapter2);
                }else if (products.getId().equals("2")){
                    WeeklyEarningsDetails.madapter2=new WeeklyAdapterRecycle2(activity,WeeklyEarningsDetails.newOrderBeansList2);
                    WeeklyEarningsDetails.recyclerView2.setAdapter(WeeklyEarningsDetails.madapter2);
                }else if (products.getId().equals("3")){
                    WeeklyEarningsDetails.madapter2=new WeeklyAdapterRecycle2(activity,WeeklyEarningsDetails.newOrderBeansList2);
                    WeeklyEarningsDetails.recyclerView2.setAdapter(WeeklyEarningsDetails.madapter2);
                }else{


                }
*/


               // holder.layout.setBackgroundResource(R.drawable.border_empty_curve);
               // .setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }
        });







    }



    @Override
    public int getItemCount() {
        System.out.println("lengthhhhhhh"+productList.size());
        return productList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
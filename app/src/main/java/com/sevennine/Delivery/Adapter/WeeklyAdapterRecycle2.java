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
import androidx.recyclerview.widget.RecyclerView;

import com.sevennine.Delivery.Bean.Weeklybean;
import com.sevennine.Delivery.Bean.Weeklybean2;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;

import java.util.Date;
import java.util.List;


public class WeeklyAdapterRecycle2 extends RecyclerView.Adapter<WeeklyAdapterRecycle2.MyViewHolder>  {
    private List<Weeklybean2> productList;
    Activity activity;
    Fragment selectedFragment;
    SessionManager sessionManager;
    public LinearLayout linearLayout;


    public static CardView cardView;
    String dtStart;
    Date date;
    public WeeklyAdapterRecycle2(Activity activity, List<Weeklybean2> productList) {
        this.productList = productList;
        this.activity=activity;
        sessionManager = new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView amount,text,time,deliver;
        public LinearLayout layout;



        public MyViewHolder(View view) {
            super(view);
            text=view.findViewById(R.id.text);
            amount=view.findViewById(R.id.amount);
          //  tex=view.findViewById(R.id.amount);
            time=view.findViewById(R.id.time);
            deliver=view.findViewById(R.id.delivered);


        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weeklyrecy2, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    final Weeklybean2 products = productList.get(position);
    System.out.println("lksdjjklk "+products.getAmount());
   holder.text.setText(products.getEarnings());
   holder.amount.setText(products.getAmount());
   holder.time.setText(products.getTime());
   holder.deliver.setText(products.getDeliver());




      /*  holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products.getId();
                Bundle bundle = new Bundle();
                bundle.putString("Ids",products.getId());

                holder.text.setTextColor(Color.parseColor("#fff"));
                holder.amount.setTextColor(Color.parseColor("#fff"));

                holder.layout.setBackgroundResource(R.drawable.border_empty_curve);
               // .setBackgroundResource(R.drawable.black_bordr_white_filled1);

            }
        });

*/





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
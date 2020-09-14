package com.sevennine.Delivery.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevennine.Delivery.Bean.NewOrderBean;
import com.sevennine.Delivery.Bean.PickupItemsBean;
import com.sevennine.Delivery.Fragment.HomeFragment;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;

import java.util.Date;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


public class PickupItemsAdapter extends RecyclerView.Adapter<PickupItemsAdapter.MyViewHolder>  {
    private List<PickupItemsBean> productList;
    Activity activity;
    Fragment selectedFragment;
    SessionManager sessionManager;
    public LinearLayout linearLayout;
    String phonestr,modestr,acceptorderid,amount,payuid,addrid,image,storelat,storelang,custlat,custlang,quantity;
    public static CardView cardView;
    String dtStart;
    Date date;
    public PickupItemsAdapter(Activity activity, List<PickupItemsBean> productList) {
        this.productList = productList;
        this.activity=activity;
        sessionManager = new SessionManager(activity);

    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;



        public MyViewHolder(View view) {
            super(view);
            item_name=view.findViewById(R.id.item_name);


        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pickup_items, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
    final PickupItemsBean products = productList.get(position);
    holder.item_name.setText(products.getItem_count()+" * "+products.getItem_name()+" - "+products.getQuantity());
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
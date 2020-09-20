package com.sevennine.Delivery.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.sevennine.Delivery.Adapter.OrderAdapter;
import com.sevennine.Delivery.Adapter.OrdersListAdapter;
import com.sevennine.Delivery.Bean.NewOrderBean;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;
import com.sevennine.Delivery.Urls;
import com.sevennine.Delivery.Volly_class.Crop_Post;
import com.sevennine.Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

//Our class extending fragment
public class OrdersFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener   {
    RecyclerView recyclerView;
    public static OrdersListAdapter madapter;
    public static List<NewOrderBean> newOrderBeansList = new ArrayList<>();
    SessionManager sessionManager;
    SwipeRefreshLayout mSwipeRefreshLayout;
    JSONArray jsonArray;
    NewOrderBean bean;
    LinearLayout back_feed;
    Fragment selectedFragment;
   String distance1,time1;
    String cust_lat,cust_lang,store_lat,store_lang;
    public static OrdersFragment newInstance() {
        OrdersFragment itemOnFragment = new OrdersFragment();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders_recycler_layout, container, false);
        // sessionManager = new SessionManager(getActivity());
        recyclerView=view.findViewById(R.id.new_order_recy);
        back_feed=view.findViewById(R.id.back_feed);
        sessionManager=new SessionManager(getActivity());
        mSwipeRefreshLayout = view.findViewById(R.id.swifeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
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
        back_feed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = HomeFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("dhsksw");
                transaction.commit();
            }
        });

       // gpsTracker = new GPSTracker(getActivity());
        newOrderBeansList.clear();
        GridLayoutManager mLayoutManager_farm = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager_farm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        madapter=new OrdersListAdapter(getActivity(),newOrderBeansList);
        recyclerView.setAdapter(madapter);
        Newdata();

        return view;
        
    }

public  void  Newdata(){
    mSwipeRefreshLayout.setRefreshing(true);
    try{
        newOrderBeansList.clear();
        final JSONObject jsonObject = new JSONObject();
      //  jsonObject.put("UserId",sessionManager.getRegId("userId"));
        System.out.println("GetOrderslist"+jsonObject);
        Crop_Post.crop_posting(getActivity(), Urls.GetOrderslist, jsonObject, new VoleyJsonObjectCallback() {
            @Override
            public void onSuccessResponse(JSONObject result) {
                System.out.println("iGetOrderslistdetails"+result);
                try{
                    jsonArray = result.getJSONArray("orderfromcart");
                    for(int i=0;i<jsonArray.length();i++){
                         final JSONObject jsonObject_main_1 = jsonArray.getJSONObject(i);
                        cust_lat=jsonObject_main_1.getString("CustLatitude");
                        cust_lang=jsonObject_main_1.getString("CustLongitude");
                        store_lat=jsonObject_main_1.getString("Latitude");
                        store_lang=jsonObject_main_1.getString("Longitude");

                        try{
                            //  newOrderBeansList.clear();
                            final JSONObject jsonObject = new JSONObject();
                            //  jsonObject.put("UserId",sessionManager.getRegId("userId"));
                            System.out.println("gffdddiiiiissttaa"+jsonObject);
                            Crop_Post.post_no_load(getActivity(), "https://maps.googleapis.com/maps/api/distancematrix/json?units=kilometers&origins="+store_lat+","+store_lang+"&destinations="+cust_lat+","+cust_lang+"&key=AIzaSyADzIqS3z-iT3MPnrC9aYusffruUuaj6tY", jsonObject, new VoleyJsonObjectCallback() {
                                @Override
                                public void onSuccessResponse(JSONObject result) {
                                    System.out.println("diiiiissttaa"+result);
                                    try{
                                        JSONArray jsonArray_distance = result.getJSONArray("rows");
                                        for(int i=0;i<jsonArray_distance.length();i++){
                                            JSONObject jsonObject1 = jsonArray_distance.getJSONObject(i);
                                            JSONArray jsonArray1=jsonObject1.getJSONArray("elements");
                                            System.out.println("jsoooooonnnnn"+jsonArray1);
                                            for(int j=0;j<jsonArray1.length();j++){
                                                JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                                JSONObject jsonObject3 =jsonObject2.getJSONObject("distance");
                                                String distance=jsonObject3.getString("text");
                                                JSONObject jsonObject4 =jsonObject2.getJSONObject("duration");
                                               String time=jsonObject4.getString("text");

                                               distance1=distance;
                                               time1=time;
                                                System.out.println("diiiiiissttta "+distance1+time1);
                                                bean=new NewOrderBean(jsonObject_main_1.getString("AcceptOrdersId"),jsonObject_main_1.getString("FirstName"),jsonObject_main_1.getString("ProductInfo"),jsonObject_main_1.getString("Amount"),jsonObject_main_1.getString("mode"),jsonObject_main_1.getString("SellingListIcon"),jsonObject_main_1.getString("Latitude"),jsonObject_main_1.getString("Longitude"),jsonObject_main_1.getString("CreatedOn"),jsonObject_main_1.getString("CustLongitude"),jsonObject_main_1.getString("CustLatitude"),jsonObject_main_1.getString("Phone"),jsonObject_main_1.getString("ProductName"),jsonObject_main_1.getString("ProductIcon"),jsonObject_main_1.getString("PayUTransactionId"),jsonObject_main_1.getString("SelectedQuantity"),distance1,time1,jsonObject_main_1.getString("StoreName"),jsonObject_main_1.getString("AddressLine1"),jsonObject_main_1.getString("AddressLine2"));
                                                newOrderBeansList.add(bean);

                                            }
                                            madapter=new OrdersListAdapter(getActivity(),newOrderBeansList);
                                            recyclerView.setAdapter(madapter);
                                            madapter.notifyDataSetChanged();
                                        }

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        System.out.println("validddaate "+distance1+time1);



                        //jsonObject1.getString("Phone")

                    }

                   /* if ((!(cust_lat.equals("")))&&(!(cust_lang.equals("")))&&(!(store_lang.equals("")))&&(!(store_lat.equals("")))){
                        madapter=new OrdersListAdapter(getActivity(),newOrderBeansList);
                        recyclerView.setAdapter(madapter);
                        madapter.notifyDataSetChanged();
                    }*/

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }catch (Exception e){
        e.printStackTrace();
    }
    mSwipeRefreshLayout.setRefreshing(false);
}
    @Override
    public void onRefresh(){
        Newdata();
       // sessionManager.saveLatLng(String.valueOf(gpsTracker.getLatitude()), String.valueOf(gpsTracker.getLongitude()));

    }
    public void distance() {

        try{
            //  newOrderBeansList.clear();
            final JSONObject jsonObject = new JSONObject();
            //  jsonObject.put("UserId",sessionManager.getRegId("userId"));
            System.out.println("gffdddiiiiissttaa"+jsonObject);
            Crop_Post.post_no_load(getActivity(), "https://maps.googleapis.com/maps/api/distancematrix/json?units=kilometers&origins="+store_lat+","+store_lang+"&destinations="+cust_lat+","+cust_lang+"&key=AIzaSyADzIqS3z-iT3MPnrC9aYusffruUuaj6tY", jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("diiiiissttaa"+result);
                    try{
                        JSONArray jsonArray = result.getJSONArray("rows");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            JSONArray jsonArray1=jsonObject1.getJSONArray("elements");
                            System.out.println("jsoooooonnnnn"+jsonArray1);
                            for(int j=0;j<jsonArray1.length();j++){
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                JSONObject jsonObject3 =jsonObject2.getJSONObject("distance");
                           //   distance1=jsonObject3.getString("text");
                            //    JSONObject jsonObject4 =jsonObject2.getJSONObject("duration");
                            //  time1=jsonObject4.getString("text");

                              //  System.out.println("diiiiiissttta "+distance1+time1);

                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

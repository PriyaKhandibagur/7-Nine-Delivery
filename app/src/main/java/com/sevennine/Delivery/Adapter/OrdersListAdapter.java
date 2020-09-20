package com.sevennine.Delivery.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.sevennine.Delivery.Bean.NewOrderBean;
import com.sevennine.Delivery.Fragment.HomeFragment;
import com.sevennine.Delivery.Fragment.OrdersFragment;
import com.sevennine.Delivery.Person;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;
import com.sevennine.Delivery.StoreDeliveryLocationDetails;
import com.sevennine.Delivery.Urls;
import com.sevennine.Delivery.Volly_class.Crop_Post;
import com.sevennine.Delivery.Volly_class.Login_post;
import com.sevennine.Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.LOCATION_SERVICE;


public class OrdersListAdapter extends RecyclerView.Adapter<OrdersListAdapter.MyViewHolder> implements LocationListener {
    private List<NewOrderBean> productList;
    public static List<NewOrderBean> newOrderBeansList = new ArrayList<>();
    Activity activity;
    Fragment selectedFragment;
    SessionManager sessionManager;
    JSONArray jsonArray, jsonArray1;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    double currentlat, currentlang;
    public LinearLayout linearLayout;
    public static String total_amount, modestr, acceptorderid, amount, payuid, addrid, image, storelat, storelang, custlat, custlang, cust_addr, cust_name;
    public static CardView cardView;
    String dtStart;
    Date date;
    LatLng store_lat, customer_lat;
    double storelatitude, storelongitude, customerlatitude, customerlongitude;
    String distance, time;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mProfileRef = firebaseDatabase.getReference();

    public OrdersListAdapter(Activity activity, List<NewOrderBean> productList) {
        this.productList = productList;
        this.activity = activity;
        sessionManager = new SessionManager(activity);
        setHasStableIds(true);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView storename, store_address, customer_name, customer_address, last_mile, first_mile, accept, reject, amount, distance, time_taking;


        public MyViewHolder(View view) {
            super(view);
            storename = view.findViewById(R.id.store_name);
            store_address = view.findViewById(R.id.store_address);
            customer_name = view.findViewById(R.id.customer_name);
            customer_address = view.findViewById(R.id.customer_address);
            accept = view.findViewById(R.id.accept);
            reject = view.findViewById(R.id.reject);
            amount = view.findViewById(R.id.amount);
            distance = view.findViewById(R.id.distance);
            time_taking = view.findViewById(R.id.time_taking);
            first_mile = view.findViewById(R.id.first_mile);
            last_mile = view.findViewById(R.id.last_mile);
            //  getMyLocation();
            //   distance();

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_item, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final NewOrderBean products = productList.get(position);
        acceptorderid = products.getProd_name();
        amount = products.getProd_price();
        payuid = products.getPayuid();
        addrid = products.getAddr();
        image = products.getImage();
        storelat = products.getLatitude();
        storelang = products.getLongitude();
        custlat = products.getCustlat();
        custlang = products.getCustlong();

        System.out.println("lkdjhgfjkls" + currentlat);
      //  getMyLocation();

       /* store_lat = new LatLng(Double.parseDouble(storelat), Double.parseDouble(storelang));
        customer_lat = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlang));*/
        if (!(custlat.equals("") && custlang.equals(""))) {
            storelatitude = Double.parseDouble(products.getLatitude());
            storelongitude = Double.parseDouble(products.getLongitude());
            customerlatitude = Double.parseDouble(products.getCustlong());
            customerlongitude = Double.parseDouble(products.getCustlat());


            holder.distance.setText(products.getDistance());
            holder.time_taking.setText(products.getTiming());
            holder.customer_name.setText(products.getUsername());
            holder.customer_address.setText(products.getAddr());
            holder.amount.setText(products.getProd_price());
            holder.storename.setText(products.getStorename());
            holder.store_address.setText(products.getStoreaddress1()+","+products.getStoreaddress2());
        }
                 Location startPoint=new Location("locationA");
           startPoint.setLatitude(14.6665970992124);
           startPoint.setLongitude(75.48478469252586);

           Location endPoint=new Location("locationA");
           endPoint.setLatitude(Double.parseDouble(storelat));
           endPoint.setLongitude(Double.parseDouble(storelang));

           double distance=startPoint.distanceTo(endPoint);
           double distance_km=(distance/1000);

           double distance_round = (int)(Math.round(distance_km * 100))/100.0;
           System.out.println("diiiiii "+distance);
           holder.first_mile.setText(distance_round+" Km");

        Location startPoint_last=new Location("locationB");
       // System.out.println("diiiiiirsdfs "+currentlat+","+currentlang);

        startPoint_last.setLatitude(14.6665970992124);
        startPoint_last.setLongitude(75.48478469252586);

        Location endPoint_last=new Location("locationB");
        endPoint_last.setLatitude(Double.parseDouble(custlang));
        endPoint_last.setLongitude(Double.parseDouble(custlat));

        double distance_last=startPoint_last.distanceTo(endPoint_last);
        double distance_km_last=(distance_last/1000);

        double distance_round_last = (int)(Math.round(distance_km_last * 100))/100.0;
        System.out.println("diiiiii "+distance_round_last);
        holder.last_mile.setText(distance_round_last+" Km");
        //Displaying the distance

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // AlertMessage();
                custlat=products.getCustlat();
                custlang=products.getCustlong();
                storelat=products.getLatitude();
                storelang=products.getLongitude();
                cust_name=products.getUsername();
                cust_addr=products.getAddr();
                total_amount=products.getProd_price();
                store_lat = new LatLng(Double.parseDouble(storelat), Double.parseDouble(storelang));
                customer_lat = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlang));
                String userId = user.getUid();
                String firstmile=holder.first_mile.getText().toString();
                String lastmile=holder.last_mile.getText().toString();
                String distance_total=holder.distance.getText().toString();
                String timing=holder.time_taking.getText().toString();
               // stores.child(userId).push().setValue(store);

                // DatabaseReference postsRef = mProfileRef.child("OrderSummary");
                String key = mProfileRef.child("Order Summary").push().getKey();
                System.out.println("pushkeyidddd "+key);

               /* mProfileRef.child(userId).child("Order Summary").child(key).setValue(new Person(products.getPayuid(), "Siddivinayaka stores","#201, Siddivinayaka stores, Vidya Nagar Byadgi, near BESM College",store_lat,cust_name,cust_addr,customer_lat,firstmile,lastmile,distance_total,timing,total_amount));
                sessionManager.saveorderkey(key);*/
                sessionManager.saveorderdetails(products.getStorename(),holder.store_address.getText().toString(),products.getUsername(),products.getAddr(),products.getProd_price(),products.getLatitude(),products.getLongitude(),products.getCustlat(),products.getCustlong(),products.getPayuid());

              //  String mGroupId = mProfileRef.child("Order Summary").getKey();

             //   System.out.println("pushkeyidddd "+mGroupId);

                Bundle bundle=new Bundle();
              bundle.putString("status","accept");
              bundle.putString("customer_name",products.getUsername());
              bundle.putString("customer_address",products.getAddr());
              bundle.putString("customer_address",products.getAddr());
                selectedFragment = HomeFragment.newInstance();
                FragmentTransaction transaction = ((FragmentActivity)activity).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                selectedFragment.setArguments(bundle);
                transaction.commit();

            }
        });


    }

    private void distance() {

        try{
          //  newOrderBeansList.clear();
            final JSONObject jsonObject = new JSONObject();
          //  jsonObject.put("UserId",sessionManager.getRegId("userId"));
            System.out.println("gffdddiiiiissttaa"+jsonObject);
            Crop_Post.post_no_load(activity, "https://maps.googleapis.com/maps/api/distancematrix/json?units=kilometers&origins="+currentlat+","+currentlang+"&destinations="+custlat+","+custlang+"&key=AIzaSyADzIqS3z-iT3MPnrC9aYusffruUuaj6tY", jsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("diiiiissttaa"+result);
                    try{
                        jsonArray = result.getJSONArray("rows");
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            jsonArray1=jsonObject1.getJSONArray("elements");
                            System.out.println("jsoooooonnnnn"+jsonArray1);
                            for(int j=0;j<jsonArray1.length();j++){
                                JSONObject jsonObject2 = jsonArray1.getJSONObject(j);
                                JSONObject jsonObject3 =jsonObject2.getJSONObject("distance");
                                distance=jsonObject3.getString("text");
                                JSONObject jsonObject4 =jsonObject2.getJSONObject("duration");
                                time=jsonObject4.getString("text");

                                System.out.println("diiiiiissttta "+distance+time);

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

    private void getMyLocation(){

        mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {

            if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            } else {
                activity.requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, 100);
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
       System.out.println("latitudeeeee "+location.getLatitude()+", "+location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }





   /* int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {

            if (activity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,50000,0,mLocationListener);

            }else{
                activity.requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },100);
            }
        }
    }*/
    @Override
    public int getItemCount() {
       // return productList.size();

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
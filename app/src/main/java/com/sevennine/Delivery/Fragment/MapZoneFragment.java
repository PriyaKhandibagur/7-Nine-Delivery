package com.sevennine.Delivery.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.maps.android.clustering.ClusterManager;
import com.sevennine.Delivery.Bean.ClusterBean;
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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

//Our class extending fragment
public class MapZoneFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener{

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    protected LatLng start = null;
    protected LatLng end = null;
    Fragment selectedFragment;
    SessionManager sessionManager;
    private BottomSheetBehavior mBottomSheetBehavior1;
    View sheetView;
    ImageView map_nav_arrow;
    private static final int MY_REQUEST_CODE = 7114;
    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;
    LinearLayout linearLayout;
    TextView store_name,storeaddress,pickupid;
    ImageView fab;
    //polyline object
    private ClusterManager<ClusterBean> mClusterManager;
    public static List<NewOrderBean> newOrderBeansList = new ArrayList<>();
    JSONArray jsonArray;
    String cust_lat,cust_lang,store_lat,store_lang;

    public static MapZoneFragment newInstance() {
        MapZoneFragment itemOnFragment = new MapZoneFragment();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

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
        sessionManager=new SessionManager(getActivity());

        requestPermision();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        return view;

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;


        setupMap();
        mClusterManager.cluster();

    }

    private void setupMap() {
        mClusterManager = new ClusterManager<ClusterBean>(getActivity(),mMap);
        getMyLocation();

        addItems();
        VenueMarkerRender renderer = new VenueMarkerRender(getActivity(), mMap, mClusterManager);
        mClusterManager.setRenderer(renderer);

    }
    private void addItems() {

        try{
            newOrderBeansList.clear();
            final JSONObject jsonObject = new JSONObject();
            //  jsonObject.put("UserId",sessionManager.getRegId("userId"));
            System.out.println("GetOrderslist"+jsonObject);
            Crop_Post.post_no_load(getActivity(), Urls.GetOrderslist, jsonObject, new VoleyJsonObjectCallback() {
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
                            double lat=Double.parseDouble(store_lat);
                            double lang=Double.parseDouble(store_lang);
                            // Create a cluster item for the marker and set the title and snippet using the constructor.
                            ClusterBean infoWindowItem = new ClusterBean(lat, lang, "1");

// Add the cluster item (marker) to the cluster manager.
                            mClusterManager.addItem(infoWindowItem);

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
    /* private void setupMap() {
         mClusterManager = ClusterManager<ClusterBean>(getActivity(), mMap);
     }*/
    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        } else {
            locationPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //if permission granted.
                    locationPermission = true;
                  //  getMyLocation();
                    System.out.println("lkjhgfgdxsdsz");


                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }
    private void getMyLocation(){

        // Getting LocationManager object from System Service LOCATION_SERVICE
        mLocationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

                mMap.clear();
                start = new LatLng(location.getLatitude(), location.getLongitude());

                //  System.out.println("mylocationnn "+start);

                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        start, 18f);
                mMap.moveCamera(cameraUpdate);
              //  mClusterManager = new ClusterManager<ClusterBean>(getActivity(), mMap);
                // Point the map's listeners at the listeners implemented by the cluster
                // manager.
             //   mMap.setOnCameraIdleListener((GoogleMap.OnCameraIdleListener) mClusterManager);
             //   mMap.setOnMarkerClickListener(mClusterManager);

                // Add cluster items (markers) to the cluster manager.
               // addItems();
            }

            private void addItems() {

                try{
                    newOrderBeansList.clear();
                    final JSONObject jsonObject = new JSONObject();
                    //  jsonObject.put("UserId",sessionManager.getRegId("userId"));
                    System.out.println("GetOrderslist"+jsonObject);
                    Crop_Post.post_no_load(getActivity(), Urls.GetOrderslist, jsonObject, new VoleyJsonObjectCallback() {
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
                                    double lat=Double.parseDouble(store_lat);
                                    double lang=Double.parseDouble(store_lang);
                                    // Create a cluster item for the marker and set the title and snippet using the constructor.
                                    ClusterBean infoWindowItem = new ClusterBean(lat, lang, "1");

// Add the cluster item (marker) to the cluster manager.
                                    mClusterManager.addItem(infoWindowItem);

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

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        int currentApiVersion = Build.VERSION.SDK_INT;
        if (currentApiVersion >= Build.VERSION_CODES.M) {

            if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
                mMap.setMyLocationEnabled(true);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,50000,0,mLocationListener);

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mMap.getUiSettings().setMapToolbarEnabled(true);

                        System.out.println("kdshkjsf"+"onmapclick");


                    }
                });

            }else{
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },100);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            System.out.println("asdfghjkl");
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                Toast.makeText(getActivity(),"Reached",Toast.LENGTH_LONG).show();
                selectedFragment = AddressDetailAfterPickupFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("asdfg");
                transaction.commit();
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

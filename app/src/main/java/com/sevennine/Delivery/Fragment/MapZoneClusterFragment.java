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
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

//Our class extending fragment
public class MapZoneClusterFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    protected LatLng start = null;
    protected LatLng end = null;
    Fragment selectedFragment;
    private BottomSheetBehavior mBottomSheetBehavior1;
    View sheetView;
    ImageView map_nav_arrow;
    TextView customer_address,customer_name;
    private static final int MY_REQUEST_CODE = 7114;
    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;
    SessionManager sessionManager;
    LinearLayout linearLayout,call_instuction;
    //polyline object
    private List<Polyline> polylines = null;
    ImageView fab;
    public static List<NewOrderBean> newOrderBeansList = new ArrayList<>();
    private ClusterManager<ClusterBean> clusterManager;
    JSONArray jsonArray;
    String cust_lat,cust_lang,store_lat,store_lang;
    public static MapZoneClusterFragment newInstance() {
        MapZoneClusterFragment itemOnFragment = new MapZoneClusterFragment();
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
                    selectedFragment = HomeFragment.newInstance();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout1, selectedFragment);
                    transaction.addToBackStack("dhskswa");
                    transaction.commit();
                   /* FragmentManager fm = getFragmentManager();
                    fm.popBackStack();*/
                    return true;
                }
                return false;
            }
        });
        requestPermision();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        return view;

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       // getMyLocation();
        setUpClusterManager(googleMap);


    }

    private void setUpClusterManager(GoogleMap googleMap){
        clusterManager = new ClusterManager(getActivity(), googleMap);
        googleMap.setOnCameraIdleListener(clusterManager);
        getMyLocation();
      //  List<ClusterBean> items = getItems();
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
                            clusterManager.addItem(infoWindowItem);  // 4

                        }



                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
          // 5
        clusterManager.cluster();

    }

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
                 //   getMyLocation();
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
                //  String custlat = getIntent().getExtras().getString("customerlatitude");
                //  String custlong = getIntent().getExtras().getString("customerlongitude");
                //  end = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlong));
              //  end = new LatLng(14.6665970992124,75.48478469252586);
              //  end = new LatLng(14.6665970992124,75.48478469252586);
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
                                    clusterManager.addItem(infoWindowItem);  // 4

                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
                // 5
                clusterManager.cluster();
                System.out.println("dhjdhlk "+end+","+start);
                //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,16));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        start, 16f);
                mMap.animateCamera(cameraUpdate);


                //  String lat = getIntent().getExtras().getString("latidkey");
                //  String longitude = getIntent().getExtras().getString("langidkey");

                // mMap.clear();
              //  Findroutes(start,end);

               /* map_nav_arrow.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        if(map_nav_arrow.getDrawable().getConstantState().equals(map_nav_arrow.getContext().getDrawable(R.drawable.ic_right_angle_arrow).getConstantState())){
                           map_nav_arrow.setImageResource(R.drawable.map_direction_icon);
                           // call_instuction.setVisibility(View.VISIBLE);
                        }else{
                            String uri = "http://maps.google.com/maps?saddr=" + location.getLatitude() + "," + location.getLongitude() + "&daddr=" + 14.6665970992124 + "," + 75.48478469252586;
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            startActivity(intent);
                        }

                    }
                });*/

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
                       /* String lat = getIntent().getExtras().getString("latidkey");
                        String longitude = getIntent().getExtras().getString("langidkey");
                        String custlat = getIntent().getExtras().getString("custlatidkey");
                        String custlong = getIntent().getExtras().getString("custlangidkey");
                        mDestination = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlong));
                        mMap.clear();
                        mMarkerOptions = new MarkerOptions().position(mDestination).title("Destination");
                        mMap.addMarker(mMarkerOptions);
                        if(mOrigin != null && mDestination != null)
                        drawRoute();
                        else if (mOrigin==mDestination){
                            reached_loc.setVisibility(View.VISIBLE);
                        }
*/                    }
                });

            }else{
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },100);
            }
        }
    }
    public void Findroutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(getActivity(), "Unable to get location", Toast.LENGTH_LONG).show();
        } else {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyADzIqS3z-iT3MPnrC9aYusffruUuaj6tY")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    //Routing call back functions.
    @Override
    public void onRoutingFailure(RouteException e) {
        View parentLayout = getActivity().findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, e.toString(), Snackbar.LENGTH_LONG);
        snackbar.show();
//        Findroutes(start,end);
    }


    @Override
    public void onRoutingStart() {
        //  Toast.makeText(DirectionsMapActivity.this,"Finding Route...",Toast.LENGTH_LONG).show();
    }


    //If Route finding success..
    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(start);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        if (polylines != null) {
            polylines.clear();
        }
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng = null;
        LatLng polylineEndLatLng = null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i < route.size(); i++) {

            if (i == shortestRouteIndex) {
             //   polyOptions.color(getActivity().getResources().getColor(R.color.blue));
                polyOptions.color(ContextCompat.getColor(getActivity(), R.color.blue));
                polyOptions.width(12);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng = polyline.getPoints().get(0);
                int k = polyline.getPoints().size();
                polylineEndLatLng = polyline.getPoints().get(k - 1);
                polylines.add(polyline);

            } else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);
    }

    @Override
    public void onRoutingCancelled() {
        Findroutes(start, end);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Findroutes(start, end);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_REQUEST_CODE) {
            System.out.println("asdfghjkl");
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                selectedFragment = ReachedDropLocationFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("dhskswadkw");
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
}

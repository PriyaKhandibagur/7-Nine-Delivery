package com.sevennine.Delivery.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sevennine.Delivery.Activity.LandingPage;
import com.sevennine.Delivery.DirectionsJSONParser;
import com.sevennine.Delivery.DirectionsMapActivity;
import com.sevennine.Delivery.MainActivity;
import com.sevennine.Delivery.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
public class PickupLocationMapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {

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
    private static final int MY_REQUEST_CODE = 7114;
    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;
    LinearLayout linearLayout;
    FloatingActionButton fab;
    //polyline object
    private List<Polyline> polylines = null;

    public static PickupLocationMapFragment newInstance() {
        PickupLocationMapFragment itemOnFragment = new PickupLocationMapFragment();
        return itemOnFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_main_layout, container, false);
        // sessionManager = new SessionManager(getActivity());
        linearLayout=view.findViewById(R.id.bottom_sheet1);
        map_nav_arrow=view.findViewById(R.id.map_nav_arrow);
        fab=view.findViewById(R.id.fab);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = AddressDetailAfterPickupFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout1, selectedFragment);
                transaction.addToBackStack("asdfg");
                transaction.commit();
            }
        });
        requestPermision();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);

        mBottomSheetBehavior1 = BottomSheetBehavior.from(linearLayout);


        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBottomSheetBehavior1.setState(BottomSheetBehavior.STATE_HIDDEN);

// set the peek height
        mBottomSheetBehavior1.setPeekHeight(500);

// set hideable or not
        mBottomSheetBehavior1.setHideable(false);

// set callback for changes

        mBottomSheetBehavior1.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        return view;
        
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        getMyLocation();


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
                    getMyLocation();
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
              //  start = new LatLng(14.6665970992124,75.48478469252586);
                System.out.println("mylocationnn "+start);
                //  String custlat = getIntent().getExtras().getString("customerlatitude");
                //  String custlong = getIntent().getExtras().getString("customerlongitude");
                //  end = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlong));
                end = new LatLng(14.6665970992124,75.48478469252586);

                System.out.println("dhjdhlk "+end+","+start);
                //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,16));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        start, 18f);
                mMap.animateCamera(cameraUpdate);

                //  String lat = getIntent().getExtras().getString("latidkey");
                //  String longitude = getIntent().getExtras().getString("langidkey");

                // mMap.clear();
                Findroutes(start,end);

                map_nav_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uri = "http://maps.google.com/maps?saddr=" + location.getLatitude() + "," + location.getLongitude() + "&daddr=" +14.6665970992124+ "," +75.48478469252586;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                });

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
                        android.Manifest.permission.ACCESS_FINE_LOCATION
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
              //  polyOptions.color(getResources().getColor(R.color.blue));
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
}

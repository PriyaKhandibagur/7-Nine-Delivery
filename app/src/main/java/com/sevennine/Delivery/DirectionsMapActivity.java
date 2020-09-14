package com.sevennine.Delivery;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sevennine.Delivery.Bean.NewOrderBean;
import com.sevennine.Delivery.Volly_class.Crop_Post;
import com.sevennine.Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

public class DirectionsMapActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener, RoutingListener {

    //google map object
    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    //current and destination location objects
    Location myLocation = null;
    Location destinationLocation = null;
    protected LatLng start = null;
    protected LatLng end = null;

    //to get location permissions.
    private final static int LOCATION_REQUEST_CODE = 23;
    boolean locationPermission = false;

    //polyline object
    private List<Polyline> polylines = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //request location permission.
        requestPermision();

        //init google map fragment to show map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);


    }

    private void requestPermision() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
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

    //to get user location
   /* private void getMyLocation() {
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                System.out.println("dncjdsjksk");
                mMap.clear();
                myLocation = location;
              //  LatLng ltlng = new LatLng(location.getLatitude(), location.getLongitude());

              //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ltlng,16));

             //   String lat = getIntent().getExtras().getString("latidkey");
             //   String longitude = getIntent().getExtras().getString("langidkey");
              //  String custlat = getIntent().getExtras().getString("custlatidkey");
              //  String custlong = getIntent().getExtras().getString("custlangidkey");
                start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                end = new LatLng(14.666061401367188,75.49584282998330);
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        start, 14f);
                mMap.animateCamera(cameraUpdate);

                //  mMap.clear();
              //  double storelat=Double.parseDouble(lat);
              //  double storelong=Double.parseDouble(longitude);
              //  start = new LatLng(storelat, storelong);
                System.out.println("kdsfhkjshf "+end+"  "+start);

               *//* if (start==end){

                }*//*

                //start route finding
                Findroutes(start, end);
            }
        });

        //get destination location when user click on map
       *//* mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                // end=latLng;
             //   end = new LatLng(14.56789, 75.890765);

               // mMap.clear();

                start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                System.out.println("hfgfddfg " + start);
                //start route finding
                Findroutes(start, end);

                String lat = getIntent().getExtras().getString("latidkey");
                String longitude = getIntent().getExtras().getString("langidkey");
                String custlat = getIntent().getExtras().getString("custlatidkey");
                String custlong = getIntent().getExtras().getString("custlangidkey");

                end = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlong));
                mMap.clear();
                //  mMap.clear();
                double storelat=Double.parseDouble(lat);
                double storelong=Double.parseDouble(longitude);
               // start = new LatLng(storelat, storelong);
                  start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
                System.out.println("kdsfhkjshf "+end+"  "+start);

                //start route finding
                Findroutes(start, end);
            }
        });*//*

    }
*/


    private void getMyLocation(){

        // Getting LocationManager object from System Service LOCATION_SERVICE
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                mMap.clear();
                start = new LatLng(location.getLatitude(), location.getLongitude());
              //  String custlat = getIntent().getExtras().getString("customerlatitude");
              //  String custlong = getIntent().getExtras().getString("customerlongitude");
              //  end = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlong));
                 end = new LatLng(14.6665970992124,75.48478469252586);

                System.out.println("dhjdhlk "+end+","+start);
                //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start,16));
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        start, 16f);
                mMap.animateCamera(cameraUpdate);

                //  String lat = getIntent().getExtras().getString("latidkey");
                //  String longitude = getIntent().getExtras().getString("langidkey");

                // mMap.clear();
                Findroutes(start,end);
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

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_DENIED) {
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      //  mMap.clear();
       /* String lat = getIntent().getExtras().getString("latidkey");
        String longitude = getIntent().getExtras().getString("langidkey");
        String custlat = getIntent().getExtras().getString("custlatidkey");
        String custlong = getIntent().getExtras().getString("custlangidkey");
        double storelat=Double.parseDouble(lat);
        double storelong=Double.parseDouble(longitude);
        LatLng ltlng = new LatLng(storelat, storelong);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                ltlng, 16f);
        mMap.animateCamera(cameraUpdate);
        end = new LatLng(Double.parseDouble(custlat), Double.parseDouble(custlong));

        //  mMap.clear();

        start = new LatLng(storelat, storelong);
        //  start = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        System.out.println("kdsfhkjshf "+end+"  "+start);

        //start route finding
        Findroutes(start, end);*/
       getMyLocation();

    }


    // function to find Routes.
    public void Findroutes(LatLng Start, LatLng End) {
        if (Start == null || End == null) {
            Toast.makeText(DirectionsMapActivity.this, "Unable to get location", Toast.LENGTH_LONG).show();
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
        View parentLayout = findViewById(android.R.id.content);
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
                polyOptions.color(getResources().getColor(R.color.colorPrimary));
                polyOptions.width(7);
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
}
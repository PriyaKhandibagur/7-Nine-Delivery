package com.sevennine.Delivery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CustomCap;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener, GoogleMap.OnMarkerClickListener {

    private Marker myMarker;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mProfileRef = firebaseDatabase.getReference();
    GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    String value_lat = null;
    String value_lng=null;
    Button saveLocationToFirebase;
    private static final int COLOR_BLACK_ARGB = 0xff000000;
    private static final int POLYLINE_STROKE_WIDTH_PX = 12;
    private static final int PATTERN_GAP_LENGTH_PX = 20;
    private static final PatternItem DOT = new Dot();
    private static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);

    // Create a stroke pattern of a gap followed by a dot.
    private static final List<PatternItem> PATTERN_POLYLINE_DOTTED = Arrays.asList(GAP, DOT);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       // saveLocationToFirebase=findViewById(R.id.saveLocationToFirebase);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
        //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(this);
        buildGoogleApiClient();


       /* mProfileRef.child("Profile").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot child : dataSnapshot.child("0").getChildren()) {
                   // String dob = child.child("dob").getValue().toString();
                   // String dod = child.child("dod").getValue().toString();

                    String latitude = child.child("latitude").getValue().toString();
                    String longitude = child.child("longitude").getValue().toString();

                  //  String firstname = child.child("firstname").getValue().toString();
                 //   String lastname = child.child("lastname").getValue().toString();

                    LatLng location = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                    googleMap.addMarker(new MarkerOptions().position(location));
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
    }
    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onConnected(Bundle connectionHint) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        System.out.println("ksjdhjs "+mLastLocation);

    }
    @Override
    public void onConnectionFailed(ConnectionResult result) {
      //  Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }
    @Override
    public void onConnectionSuspended(int cause) {
      //  Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnMarkerClickListener(this);
      //  mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        System.out.println("ksjdhjs "+mLastLocation);
       /* if (mLastLocation != null) {
            value_lat= String.valueOf(mLastLocation.getLatitude());
            value_lng =String.valueOf(mLastLocation.getLongitude());

            System.out.println("vcsadhg"+value_lat+", "+value_lng);


        }*/

      /*  LatLng wollongong = new LatLng(14.6814,75.4869);
        myMarker = googleMap.addMarker(new MarkerOptions()
                .position(wollongong)
               *//* .title("Ben Roberts")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .snippet("05/06/1949 - 08/08/2016")*//*
                .anchor(0.0f, 1.0f)

        );*/
/*
        LatLng test2 = new LatLng(14.6812, 75.4850);
        googleMap.addMarker(new MarkerOptions()
                .position(test2)
               *//* .title("Graveyard")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                .snippet("This is a test graveyard too")*//*
                .anchor(0.0f, 1.0f)
        );*/

      /*  Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(14.6884,75.5869),
                        new LatLng(14.6874,75.7869),
                        new LatLng(14.6854,75.8869),
                        new LatLng(14.6834,75.9869)
                        *//*new LatLng(-34.747, 145.592),
                        new LatLng(-34.364, 147.891),
                        new LatLng(-33.501, 150.217),
                        new LatLng(-32.306, 149.248),
                        new LatLng(-32.491, 147.309)*//*));
        // [END maps_poly_activity_add_polyline]
        // [START_EXCLUDE silent]
        // Store a data object with the polyline, used here to indicate an arbitrary type.
        polyline1.setTag("A");
        // [END maps_poly_activity_add_polyline_set_tag]
        // Style the polyline.
        stylePolyline(polyline1);*/
        Polyline polyline2 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(14.5814,75.3869),
                        new LatLng(14.6814,75.3969),
                        new LatLng(14.7814,75.069),
                        new LatLng(14.8814,75.7869)
                        /*new LatLng(-27.456, 119.672),
                        new LatLng(-25.971, 124.187),
                        new LatLng(-28.081, 126.555),
                        new LatLng(-28.848, 124.229),
                        new LatLng(-28.215, 123.938)*/));
        polyline2.setTag("B");
        stylePolyline(polyline2);
        googleMap.setOnPolylineClickListener(this);
        saveLocationToFirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProfileRef.push().setValue("Latitude : "+14.6812 +"  &amp; Longitude : "+75.4850);
                Toast.makeText(GoogleMapActivity.this ,"Location saved to the Firebasedatabase",Toast.LENGTH_LONG).show();
            }
        });
       /* Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
                .width(5)
                .color(Color.BLUE));*/
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(14.5814,75.3869), 14));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }
    private void stylePolyline(Polyline polyline) {
        String type = "";
        // Get the data object stored with the polyline.
        if (polyline.getTag() != null) {
            type = polyline.getTag().toString();
        }

       /* switch (type) {
            // If no type is given, allow the API to use the default.
            case "A":
                // Use a custom bitmap as the cap at the start of the line.
                polyline.setStartCap(
                        new CustomCap(
                                BitmapDescriptorFactory.fromResource(R.drawable.fui_ic_mail_white_24dp), 10));
                break;
            case "B":
                // Use a round cap at the start of the line.
                polyline.setStartCap(new RoundCap());
                break;
        }
*/
        polyline.setEndCap(new RoundCap());
        polyline.setWidth(POLYLINE_STROKE_WIDTH_PX);
        polyline.setColor(COLOR_BLACK_ARGB);
        polyline.setJointType(JointType.ROUND);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        if (marker.equals(myMarker)) {
          /*  Intent intent = new Intent(MapsActivity.this, Info_window.class);
            startActivity(intent);*/
        } else {
           /* Intent intent = new Intent(MapsActivity.this, Info_window.class);
            startActivity(intent);*/
        }
        return false;

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        if ((polyline.getPattern() == null) || (!polyline.getPattern().contains(DOT))) {
            polyline.setPattern(PATTERN_POLYLINE_DOTTED);
        } else {
            // The default pattern is a solid stroke.
            polyline.setPattern(null);
        }

        Toast.makeText(this, "Route type " + polyline.getTag().toString(),
                Toast.LENGTH_SHORT).show();

    }
}
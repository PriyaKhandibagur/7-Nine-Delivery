package com.sevennine.Delivery;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.renderscript.Sampler;
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
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.protobuf.Value;

import java.util.Arrays;
import java.util.List;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

public class GoogleMapPolylineActivity extends FragmentActivity implements OnMapReadyCallback{

     MarkerOptions place1, place2;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mProfileRef = firebaseDatabase.getReference();
    GoogleMap googleMap;
    Polyline currentpolyline;
    Button saveLocationToFirebase;


    // Create a stroke pattern of a gap followed by a dot.
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
        place1= new MarkerOptions().position(new LatLng(14.54768,75.65432));
        place2= new MarkerOptions().position(new LatLng(14.64768,75.75432));

        String url=getUrl(place1.getPosition(),place2.getPosition(),"driving");

    }

    private String getUrl(LatLng origin, LatLng dest, String directionmode) {
    String str_origin="origin"+origin.latitude+","+origin.longitude;
    String str_dest="destination"+dest.latitude+","+dest.longitude;
    String mode="mode"+directionmode;
    String parameters=str_origin+"&"+str_dest+"&"+mode;
    String output="json";
    String url="http://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+"&key"+getString(R.string.google_maps_key);
    return url;
    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(place1);
        googleMap.addMarker(place2);
        currentpolyline=googleMap.addPolyline(new PolylineOptions().add(new LatLng(47.6677146,-122.3470447), new LatLng(47.6442757,-122.2814693)));
    }
}
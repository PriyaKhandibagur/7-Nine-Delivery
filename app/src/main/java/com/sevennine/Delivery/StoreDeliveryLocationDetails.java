package com.sevennine.Delivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class StoreDeliveryLocationDetails extends AppCompatActivity {

    TextView store_address,delivery_address;
    ImageView arrow_store,arrow_delivery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_address_card_lay);
        store_address=findViewById(R.id.store_address);
        delivery_address=findViewById(R.id.delivery_address);
        arrow_store=findViewById(R.id.arrow_store);
        arrow_delivery=findViewById(R.id.arrow_delivery);
       // button=findViewById(R.id.button);

         final String custlat = getIntent().getExtras().getString("custlatidkey");
         final String custlong = getIntent().getExtras().getString("custlangidkey");
         String cust_addr = getIntent().getExtras().getString("cust_address");
         final String total_amount=getIntent().getExtras().getString("totalamount");

         delivery_address.setText(cust_addr);
         store_address.setText("Vidya Nagar Park, Byadgi, Karnataka 581106, India");
         arrow_delivery.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(StoreDeliveryLocationDetails.this, DirectionsMapCustomerActivity.class);
                 Bundle extras = new Bundle();
                 // extras.putString("latidkey", latid);
                 //  extras.putString("langidkey", latid);
                 extras.putString("customerlatitude", custlat);
                 extras.putString("customerlongitude", custlong);
                 extras.putString("tamount", total_amount);
                 intent.putExtras(extras);
                 startActivity(intent);
             }
         });
        arrow_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StoreDeliveryLocationDetails.this, DirectionsMapActivity.class);
               /* Bundle extras = new Bundle();
                // extras.putString("latidkey", latid);
                //  extras.putString("langidkey", latid);
                extras.putString("custlatidkey", custlat);
                extras.putString("custlangidkey", custlong);
                extras.putString("totalamount", total_amount);

                intent.putExtras(extras);*/
                startActivity(intent);
            }
        });

    }



}

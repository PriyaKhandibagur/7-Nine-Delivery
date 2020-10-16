
package com.sevennine.Delivery.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sevennine.Delivery.Activity.LandingPage;
import com.sevennine.Delivery.Bean.DutyStatusBean;
import com.sevennine.Delivery.Bean.User;
import com.sevennine.Delivery.CustomAuthLoginActivity;
import com.sevennine.Delivery.Person;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.sevennine.Delivery.general.AppConstants.DEFAULT_PROFILE_PIC_DIRECTORY;
import static com.sevennine.Delivery.general.AppConstants.DEFAULT_PROFILE_PIC_NAME;
import static com.sevennine.Delivery.general.FirebaseConstants.TABLE_USERS;

public class HomeFragment extends Fragment implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{
    Fragment selectedFragment;
    public static DrawerLayout drawer;
    ImageView search;
    LinearLayout menu,profile_arrow;
    LabeledSwitch switch_duty;
    String userid;
    public static String switch_on;
    SessionManager sessionManager;
    TextView help,refer,earning_incentives,home,logout,floating_cash,login_history,rewards,my_orders,list_prod,inventory,account,store,offers,payments,notification;
    public static TextView cart_count_text,user_name_menu;
    static boolean fragloaded;
    boolean doubleBackToExitPressedOnce = false;
    JSONArray storelist;
    static Fragment myloadingfragment;
    ImageView map;
    CircleImageView image_acc;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mProfileRef = firebaseDatabase.getReference();
    String userId;
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_navigation_menu, container, false);
        switch_duty=view.findViewById(R.id.switch_duty);
        menu=view.findViewById(R.id.menu);
        home=view.findViewById(R.id.home);
        logout=view.findViewById(R.id.logout);
        profile_arrow=view.findViewById(R.id.profile_arrow);
        earning_incentives=view.findViewById(R.id.earning);
        refer=view.findViewById(R.id.refer);
        map=view.findViewById(R.id.map);
        floating_cash=view.findViewById(R.id.floating_cash);
        rewards=view.findViewById(R.id.rewards);
        login_history=view.findViewById(R.id.login_history);
        help=view.findViewById(R.id.help);
        image_acc=view.findViewById(R.id.image_acc);
        user_name_menu=view.findViewById(R.id.user_name_menu);
        //  shop_cat=view.findViewById(R.id.shop_cat);
        //  disc_store=view.findViewById(R.id.disc_store);
        drawer =view.findViewById(R.id.drawer_layout1);

        Window window = getActivity().getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.black));

        sessionManager=new SessionManager((getActivity()));
      /*  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();*/
        NavigationView navigationView = (NavigationView)view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        userId = user.getUid();
        sessionManager.saveuserid(userId);


        mProfileRef.child(userId).child("Duty Status").orderByChild("dutyOn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                if (dataSnapshot != null) {
                    DutyStatusBean duty = new DutyStatusBean((Boolean) dataSnapshot.getValue());
                    System.out.println("status_on " + duty.isDutyOn());

                    if (duty.isDutyOn() == true) {
                        switch_duty.setColorOn(Color.parseColor("#00BF6F"));
                        switch_duty.setOn(true);
                    } else {
                      //  switch_duty.set
                        switch_duty.setColorOn(Color.parseColor("#9F9F9F"));
                        switch_duty.setOn(false);
                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

            // ...
        });
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (doubleBackToExitPressedOnce) {

                        Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        intent1.addCategory(Intent.CATEGORY_HOME);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
                        startActivity(intent1);
                        getActivity().finish();                   }
                    // System.exit(0);

                    // home_img.setImageResource(R.drawable.ic_home_green);

                    doubleBackToExitPressedOnce = true;

                    Toast toast = Toast.makeText(getActivity(),"Please click back again to exit", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER,0,0);
                    toast.show();
                    //   Toast.makeText(getApplicationContext(), toast_click_back, Toast.LENGTH_SHORT).show();

                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce=false;
                        }
                    }, 3000);

                }

                return true;
            }
        });
        Bundle bundle=getArguments();
        if (bundle!=null){
            System.out.println("bundle_not_null "+getArguments().getString("status"));
            if (getArguments().getString("status")!=null){
                System.out.println("status_bundle_not_null");
                Bundle address_bundle=new Bundle();
                address_bundle.putString("customer_name1",getArguments().getString("customer_name"));
                address_bundle.putString("customer_address1",getArguments().getString("customer_address"));
                selectedFragment = AddressDetailFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.addToBackStack("dhskswacdw");
                selectedFragment.setArguments(address_bundle);
                transaction.commit();
            }/*else if (getArguments().getString("delivery_map_status")!=null){
                selectedFragment = DeliveryLocationMapFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.addToBackStack("dhskswfadw");
                transaction.commit();
            }*/ else {
                selectedFragment = HomeLandingFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.commit();
            }
        }else {
            selectedFragment = HomeLandingFragment.newInstance();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout_home, selectedFragment);
            transaction.commit();
        }
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedFragment = MapZoneClusterFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_home, selectedFragment);
                transaction.commit();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("uuuuuuiiidd "+userId);
                // drawer_layout =view.findViewById(R.id.drawer_layout1);
                drawer.openDrawer(GravityCompat.START);
                switch_duty.setOnToggledListener(new OnToggledListener() {
                    @Override
                    public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                        if (switch_duty.isOn()) {
                            switch_duty.setColorOn(Color.parseColor("#00BF6F"));
                            mProfileRef.child(userId).child("Duty Status").setValue(new DutyStatusBean(true));
                           /* DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            Date date = new Date();
                            String strDate = dateFormat.format(date).toString();
                            mProfileRef.child(userId).child("Duty Status").child("Duty timings").setValue(strDate);*/
                            final Dialog dialog = new Dialog(getActivity());
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.alert_dialog);

                            TextView text = (TextView) dialog.findViewById(R.id.ok);

                            text.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch_on="on";
                                    dialog.dismiss();
                                    drawer.closeDrawers();
                                    selectedFragment = SearchingOrdersFragment.newInstance();
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.searching_frame, selectedFragment);
                                    transaction.commit();

                                }
                            });

                            dialog.show();
                        }else{
                            switch_duty.setColorOn(Color.parseColor("#9F9F9F"));

                            mProfileRef.child(userId).child("Duty Status").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Boolean status = false;
                                    dataSnapshot.getRef().child("dutyOn").setValue(status);
                                    //   Toast.makeText(DirectionsRouteMapActivity.this ,"Location saved to the Firebasedatabase",Toast.LENGTH_LONG).show();

                                    // dialog.dismiss();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }

                            });
                        }
                    }
                });

                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AuthUI.getInstance()
                                .signOut(getActivity())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    public void onComplete(@NonNull Task<Void> task) {
                                        // ...
                                        Intent intent = new Intent(getActivity(), CustomAuthLoginActivity.class);
                                        startActivity(intent);
                                    }
                                });
                    }
                });
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                storageReference.child("EUsa0q4TyXY3B027u4NmALKoBXs1").child(DEFAULT_PROFILE_PIC_DIRECTORY).child(DEFAULT_PROFILE_PIC_NAME)
                        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).fit().centerInside().into(image_acc);
                    }
                });
                DatabaseReference databaseReference = firebaseDatabase.getReference("EUsa0q4TyXY3B027u4NmALKoBXs1");

                if(userId!=null) {
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User userProfile = dataSnapshot.child(TABLE_USERS).getValue(User.class);

                            user_name_menu.setText(userProfile.getFirstName());

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // onError(databaseError.getMessage());
                            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else{

                }

                profile_arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedFragment = ProfileFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });


                earning_incentives.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedFragment = EarningAndIncentives.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                floating_cash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedFragment = FloatingCash.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });

                rewards.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedFragment = RewardsDetails.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });

                login_history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedFragment = LoginHistory.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                refer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedFragment = RefernEarn.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });
                help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedFragment = HelpFragment.newInstance();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout1, selectedFragment);
                        transaction.commit();
                        drawer.closeDrawers();
                    }
                });

            }
        });
        return view;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

}


package com.sevennine.Delivery.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.sevennine.Delivery.R;
import com.sevennine.Delivery.SessionManager;
import com.sevennine.Delivery.Urls;
import com.sevennine.Delivery.Volly_class.Login_post;
import com.sevennine.Delivery.Volly_class.VoleyJsonObjectCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NewSignUpActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    public static TextView toast_text,create_acc, continue_sign_up, change_lang, backtologin, referal_text;
    LinearLayout back_feed;
    SessionManager sessionManager;
    public static EditText name, mobile_no, password, referal_code;
    String status, status_resp, message,userid,FLogin;
    JSONArray lng_array;
    Activity activity;
    JSONObject lngObject;
    public static TextView popup_heading;
    public static TextInputLayout sign_mobile, sign_pass;
    public static EditText sign_name;
    Fragment selectedFragment;
    public static String mob_toast,valid_no_toast, passwrd_toast, minimum_character_toast, enter_all_toast, name_toast, mobile_registered_toast,usernot_register, privacy_policy, toast_internet, toast_nointernet;
    String s1;

   /* GoogleApiClient mGoogleApiClient;
    private int RESOLVE_HINT = 2;
    Credential credential;*/

    LinearLayout linearLayout;
    BroadcastReceiver receiver;
    public static EditText spn_localize, phone_no;
    String localize_text;
    TextView privacy_terms, register, login,welcome_text,made_text,confluence;
    public static String contact, mob_contact;
    String refer;
    public static Dialog dialog;


    public static boolean connectivity_check;
    ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onStop() {
        unregisterReceiver(connectivityReceiver);
        super.onStop();
    }

    //
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }


    private void showSnack(boolean isConnected) {
        String message = null;
        int color = 0;
        if (isConnected) {
            if (connectivity_check) {
                message = "Good! Connected to Internet";
                color = Color.WHITE;
               /* Snackbar snackbar = Snackbar.make(linearLayout, " Good Interner", Snackbar.LENGTH_LONG);
                View sbView = snackbar.getView();
                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                textView.setBackgroundColor(ContextCompat.getColor(NewSignUpActivity.this, R.color.orange));
                textView.setTextColor(Color.WHITE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    textView.setGravity(Gravity.CENTER_HORIZONTAL);
                }

                snackbar.show();*/

                Toast toast = Toast.makeText(NewSignUpActivity.this, message, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();

                //setting connectivity to false only on executing "Good! Connected to Internet"
                connectivity_check = false;
            }


        } else {
            message = "No Internet Connection";
            color = Color.RED;
            //setting connectivity to true only on executing "Sorry! Not connected to internet"
            connectivity_check = true;
            // Snackbar snackbar = Snackbar.make(coordinatorLayout,message, Snackbar.LENGTH_LONG);
          /*  Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_LONG);
            View sb = snackbar.getView();
            TextView textView = (TextView) sb.findViewById(android.support.design.R.id.snackbar_text);
            textView.setBackgroundColor(ContextCompat.getColor(NewSignUpActivity.this, R.color.orange));
            textView.setTextColor(Color.WHITE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            snackbar.show();*/
          /*  View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(color);
            snackbar.show();*/

            Toast toast = Toast.makeText(NewSignUpActivity.this, message, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
            TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
            toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        // register connection status listener
      //  MyApplication.getInstance().setConnectivityListener(this);


    }

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login_page_screen);
       // checkConnection();

        phone_no = findViewById(R.id.phonenumber);
        register = findViewById(R.id.register_btn);
        login = findViewById(R.id.login_btn);
        linearLayout = findViewById(R.id.linear_layout);
        welcome_text = findViewById(R.id.text);
        made_text = findViewById(R.id.made_text1);
        confluence = findViewById(R.id.conflu);
      toast_text=findViewById(R.id.toast_text);


        setupUI(linearLayout);
        String[] localize = {"+91"};

        sessionManager = new SessionManager(this);
           // sessionManager.checkRegister();

      /*  try {
            lngObject = new JSONObject(sessionManager.getRegId("language"));

            made_text.setText(lngObject.getString("MadeforFarmingCommunity"));
            confluence.setText(lngObject.getString("Theconfluenceoffarmersandfairtrade"));
            welcome_text.setText(lngObject.getString("Enteryourphonenumbertogetstarted").replace("\n",""));
            register.setText(lngObject.getString("REGISTER"));
            login.setText(lngObject.getString("LOGIN"));
            mob_toast =(lngObject.getString("Enterphonenumbertoproceed"));
            valid_no_toast =(lngObject.getString("Entervalidmobilenumber"));
            mobile_registered_toast =(lngObject.getString("Useralreadyregistered"));
            usernot_register =(lngObject.getString("UsernotRegistered"));



        } catch (JSONException e) {
            e.printStackTrace();
        }
*/

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(SignUp.this, EnterOTP.class);
                startActivity(intent);
                finish();*/

                localize_text = "+91";

                contact =  phone_no.getText().toString();

                if (phone_no.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(NewSignUpActivity.this,"Enter phone number to proceed", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    toast.show();
                   // toast_text.setVisibility(View.VISIBLE);

                } else if (!(phone_no.length() == 10)) {
                    Toast toast = Toast.makeText(NewSignUpActivity.this,"Enter valid mobile number", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    toast.show();
                   /* Toast toast = Toast.makeText(NewSignUpActivity.this, valid_no_toast, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();*/

                    /*Toast toast = Toast.makeText(getApplicationContext(), "Please enter  valid phone number", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 40);
                    toast.show();*/
                } else {
                   /* toast_text.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(NewSignUpActivity.this, NewEnterOTP.class);
                    // intent.putExtra("otpnumber", status);
                    // intent.putExtra("register_status","register_btn");
                    startActivity(intent);*/
                  register();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Intent intent = new Intent(SignUp.this, EnterOTP.class);
                startActivity(intent);
                finish();*/

                localize_text = "+91";

                contact =  phone_no.getText().toString();

                if (phone_no.getText().toString().equals("")) {
                  //  toast_text.setVisibility(View.VISIBLE);

                    Toast toast = Toast.makeText(NewSignUpActivity.this,"Enter mobile number to proceed", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    toast.show();
                    /*Toast toast = Toast.makeText(NewSignUpActivity.this, mob_toast, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();*/



                } else if (!(phone_no.length() == 10)) {
                    Toast toast = Toast.makeText(NewSignUpActivity.this,"Enter valid mobile number", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    toast.show();
                    /*Toast toast = Toast.makeText(NewSignUpActivity.this, valid_no_toast, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                    TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                    toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();*/

                } else {
                    /*toast_text.setVisibility(View.INVISIBLE);*/
                   /* Intent intent = new Intent(NewSignUpActivity.this, NewEnterOTP.class);
                    intent.putExtra("otpnumber", status);
                    intent.putExtra("register_status","login_btn");
                    startActivity(intent);*/
                   login();
                }
            }
        });


    }

    private void login() {

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            JSONObject postjsonObject = new JSONObject();


            userRequestjsonObject.put("PhoneNo", phone_no.getText().toString());
            postjsonObject.putOpt("UserRequest", userRequestjsonObject);

            System.out.println("post_oobject" + postjsonObject);


            Login_post.login_posting(NewSignUpActivity.this, Urls.NEWLOGIN, postjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statussssss" + result);
                    JSONObject jsonObject = new JSONObject();
                    JSONObject jsonObject_resp = new JSONObject();

                    try {
                        jsonObject_resp = result.getJSONObject("ResultObject");
                        if (!(jsonObject_resp.isNull("user")) ){
                            jsonObject=jsonObject_resp.getJSONObject("user");
                            status_resp = jsonObject_resp.getString("Status");
                            userid = jsonObject_resp.getString("UserId");
                            System.out.println("loginUserIddddddd"+userid);
                            status = jsonObject_resp.getString("OTP");



                            if (status_resp.equals("1")) {
                                Intent intent = new Intent(NewSignUpActivity.this, NewEnterOTP.class);
                                intent.putExtra("otpnumber", status);
                                intent.putExtra("register_status","login_btn");
                                startActivity(intent);

                                sessionManager.createLoginSession(contact);
                                sessionManager.save_name(jsonObject.getString("PhoneNo"));
                                System.out.println("phonenumberrrrrrrrrrrrrrrrr"+jsonObject.getString("PhoneNo"));
                                sessionManager.saveuserid(userid);

                            }

                        } else {

                            Toast toast = Toast.makeText(NewSignUpActivity.this,"User not register", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                            toast.show();
                           /* Toast toast = Toast.makeText(NewSignUpActivity.this, usernot_register, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                            TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();*/


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





   /* public void getHintPhoneNumber() {
        HintRequest hintRequest =
                new HintRequest.Builder()
                        .setPhoneNumberIdentifierSupported(true)
                        .build();
        PendingIntent mIntent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Result if we want hint number
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                System.out.println("ewhfisksdvkdsnv" + credential.getId());
               s1=credential.getId().substring(3);
                System.out.println("ewhfisksdvkdsnv" + s1);
                //contact_no = mobile_no.getText().toString();
                //  System.out.println("uryuewyuwe" + contact_no);

                check_login_user2();
                // login_register();
            }
            // credential.getId();  <-- will need to process phone number string
            //  mobile_no.setText(credential.getId());
        }
    }
*/


   /* private void check_login_user2() {
        try{
            JSONObject jsonObject = new JSONObject();
            JSONObject post_Object = new JSONObject();
            jsonObject.put("PhoneNo",s1);
            post_Object.put("UserRequest",jsonObject);
            System.out.println("postobjj"+post_Object);
            Login_post.login_posting(NewSignUpActivity.this, Urls.NEWLOGIN,post_Object, new VoleyJsonObjectCallback()  {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("111111user" + result);
                    try{
                        JSONObject jsonObject;
                        JSONObject userObject;
                        jsonObject = result.getJSONObject("ResultObject");
                        if(!(jsonObject.isNull("user"))) {
                            userObject = jsonObject.getJSONObject("user");
                            status = jsonObject.getString("Status");
                            String status1 = jsonObject.getString("OTP");
                            userid = jsonObject.getString("UserId");
                            System.out.println("useridddduserId" + userid);
       *//*                     sessionManager.save_name(jsonObject.getString("PhoneNo"));
                            //   sessionManager.save_name(userObject.getString("FullName"),userObject.getString("PhoneNo"),userObject.getString("ProfilePic"));
                            sessionManager.saveUserId(userId);
                            System.out.println("useridddd" + mobile_no.getText().toString());
*//*
                            sessionManager.createLoginSession(userObject.getString("PhoneNo"));
                            sessionManager.save_name(userObject.getString("PhoneNo"));
                            //   sessionManager.save_name(userObject.getString("FullName"),userObject.getString("PhoneNo"),userObject.getString("ProfilePic"));
                            sessionManager.saveUserId(userid);
                            System.out.println("useriddddsaveee"+sessionManager.getRegId("phone"));
                            if ((status.equals("1"))) {
                                System.out.println("jdhyusulogin" + status);
                                sessionManager.createLoginSession(userObject.getString("PhoneNo"));
                                Intent intent = new Intent(NewSignUpActivity.this, NewEnterOTP.class);
                                intent.putExtra("otpnumber", status1);
                                intent.putExtra("register_status","login_btn");
                                startActivity(intent);
                                //    sessionManager.createRegisterSession(contact_no);
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
    }*/



    @Override
    public void onBackPressed() {




    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }

    //
    public static InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            String specialChars = ".1/*!@#$%^&*()\"{}_[]|\\?/<>,.:-'';§£¥₹...%&+=€π|";
            StringBuilder sb = new StringBuilder(end - start);
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL||type==Character.MATH_SYMBOL||specialChars.contains("" + source)) {
                    return "";
                }
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        if (dstart == 0)
                            return "";
                    }else if(Character.isDigit(source.charAt(i))) {
                        return "";
                    }
                }
                return null;

            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };

    public static InputFilter EMOJI_FILTER1 = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            boolean keepOriginal = true;
            StringBuilder sb = new StringBuilder(end - start);

            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }

                String filtered = "";
                for (int i = start; i < end; i++) {
                    char character = source.charAt(i);
                    if (!Character.isWhitespace(character)) {
                        filtered += character;
                    }
                }
                return filtered;

            }
            if (keepOriginal)
                return null;
            else {
                if (source instanceof Spanned) {
                    SpannableString sp = new SpannableString(sb);
                    TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, sp, 0);
                    return sp;
                } else {
                    return sb;
                }
            }
        }
    };

    //
    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(NewSignUpActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }
    public static void hideSoftKeyboard(Activity activity) {


        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {

            try {
                assert inputManager != null;
                inputManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (AssertionError e) {
                e.printStackTrace();
            }
        }
    }

    private void register() {

        try {
            JSONObject userRequestjsonObject = new JSONObject();
            JSONObject postjsonObject = new JSONObject();
            userRequestjsonObject.put("PhoneNo", phone_no.getText().toString());
            userRequestjsonObject.put("IsOTPVerified", 1);
            postjsonObject.putOpt("objUser", userRequestjsonObject);
            System.out.println("post_oobject" + postjsonObject);

            Login_post.login_posting(NewSignUpActivity.this, Urls.NEWSIGNUP, postjsonObject, new VoleyJsonObjectCallback() {
                @Override
                public void onSuccessResponse(JSONObject result) {
                    System.out.println("statusssssslllll" + result);
                    JSONObject jsonObject = new JSONObject();
                    JSONObject jsonObject_resp = new JSONObject();

                    try {
                        if (result.isNull("user")) {
                            jsonObject_resp = result.getJSONObject("Response");
                            status_resp = jsonObject_resp.getString("Status");

                            Toast toast = Toast.makeText(NewSignUpActivity.this,"User already registered", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                            toast.show();

                            /*Toast toast = Toast.makeText(NewSignUpActivity.this, mobile_registered_toast, Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                            TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                            toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();*/

                           /* Toast toast = Toast.makeText(getApplicationContext(), "User  already registered", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 40);
                            toast.show();*/

                            //  sessionManager.saveUserId(jsonObject_resp.getString("Id"));
                            //  sessionManager.save_name(jsonObject_resp.getString("FullName"),jsonObject_resp.getString("PhoneNo"),jsonObject_resp.getString("ProfilePic"));
//                            Intent intent = new Intent(New_Login_Activity2.this, FirmShopDetailsActivity.class);
//                            startActivity(intent);
                            // sessionManager.createRegistrSession(NewSignUpActivity.contact);

                        } else {
                            jsonObject = result.getJSONObject("user");
                            jsonObject_resp = result.getJSONObject("Response");
                            status_resp = jsonObject_resp.getString("Status");
                            status = jsonObject.getString("OTP");
                            String userid = jsonObject.getString("Id");
                            System.out.println("Registeruseerrrriidd" + userid);
                            //  sessionManager.createRegisterSession(name_text,contact,password_text);
                            sessionManager.saveuserid(userid);
                            sessionManager.save_name(jsonObject.getString("PhoneNo"));
                            // sessionManager.save_name(jsonObject.getString("FullName"), jsonObject.getString("PhoneNo"),jsonObject.getString("ProfilePic"));
                            Intent intent = new Intent(NewSignUpActivity.this, NewEnterOTP.class);
                            intent.putExtra("otpnumber", status);
                            intent.putExtra("register_status","register_btn");
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



            /*try {
                JSONObject userRequestjsonObject = new JSONObject();
                JSONObject postjsonObject = new JSONObject();

                userRequestjsonObject.put("PhoneNo", phone_no.getText().toString());
                userRequestjsonObject.put("IsOTPVerified", 1);

                postjsonObject.putOpt("objUser", userRequestjsonObject);
                System.out.println("post_oobject" + postjsonObject);

                Login_post.login_posting(NewSignUpActivity.this, Urls.NEWSIGNUP, postjsonObject, new VoleyJsonObjectCallback() {
                    @Override
                    public void onSuccessResponse(JSONObject result) {
                        System.out.println("statusssssslllll" + result);
                        JSONObject jsonObject = new JSONObject();
                        JSONObject jsonObject_resp = new JSONObject();
                        try {
                            if (result.isNull("user")) {

                                jsonObject_resp = result.getJSONObject("Response");
                                status_resp = jsonObject_resp.getString("Status");

                                Toast toast = Toast.makeText(NewSignUpActivity.this, "  User  already registered  ", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                                TextView toastMessage=(TextView) toast.getView().findViewById(android.R.id.message);
                                toastMessage.setTextColor(Color.WHITE); toast.getView().setBackgroundResource(R.drawable.black_curve_background); toast.show();
                               *//* Toast toast = Toast.makeText(getApplicationContext(), "User  already registered", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 40);
                                toast.show();
*//*
                                // sessionManager.saveUserId(jsonObject_resp.getString("Id"));
                                //  sessionManager.save_name(jsonObject_resp.getString("FullName"),jsonObject_resp.getString("PhoneNo"),jsonObject_resp.getString("ProfilePic"));
                                Intent intent = new Intent(NewSignUpActivity.this, FirmShopDetailsActivity.class);
                                startActivity(intent);
                                sessionManager.createLoginSession(NewSignUpActivity.contact);





                            } else {

                                jsonObject_resp = result.getJSONObject("Response");
                                jsonObject = result.getJSONObject("user");
                                status_resp = jsonObject_resp.getString("Status");
                                status = jsonObject.getString("OTP");
                                String userid = jsonObject.getString("Id");
                                System.out.println("useerrrriidd" + userid);
                                //  sessionManager.createRegisterSession(name_text,contact,password_text);
                                sessionManager.saveUserId(userid);
                                sessionManager.save_name(jsonObject.getString("PhoneNo"));
                                sessionManager.createLoginSession(NewSignUpActivity.contact);
                               // sessionManager.save_name(jsonObject.getString("FullName"), jsonObject.getString("PhoneNo"),jsonObject.getString("ProfilePic"));

                                    Intent intent = new Intent(NewSignUpActivity.this, NewEnterOTP.class);
                                    intent.putExtra("otpnumber", status);
                                    startActivity(intent);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }*/

    }

/*
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/
}


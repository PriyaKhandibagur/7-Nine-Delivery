package com.sevennine.Delivery;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by vinod on 11/12/17.
 */
//test
public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences pref1;
     SharedPreferences.Editor loginPrefsEditor;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
   // private static final String PREF_NAME = "ParentCraft";
    private static final String PREF_NAME = "Xohri";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // Person name (make variable public to access from outside)
    public static final String KEY_PASSWORD = "pass";
    public static final String KEY_NAME = "name";
    public static final String KEY_LANGUAGE_DETAILS= "langdetails";
    public static final String KEY_DEFAULT_ADDRESS= "default_address";
    public static final String KEY_DEFAULT_ADDRESS_ID= "default_address_id";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_ORDERID = "orderid";
    public static final String KEY_LNG_SELECTED = "lng";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_LANGUAGE_NAME = "language_name";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LNG = "longtitude";
    public static final String KEY_STORENAME = "storename";
    public static final String KEY_STOREADDRESS = "storeaddress";
    public static final String KEY_CUSTNAME = "custname";
    public static final String KEY_CUSTADDRESS = "custaddress";
    public static final String KEYTOTALAMOUNT = "totalamount";
    public static final String KEY_STORELAT = "storelat";
    public static final String KEY_STORELANG = "storelang";
    public static final String KEY_CUSTLAT = "custlat";
    public static final String KEY_CUSTLANG = "custlang";
    public static final String KEY_PICKUPID = "pickupid";
   // public static final String KEY_LOCATION ="loc";

    public static final String KEY_LOCATION = "location";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        pref1 = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        loginPrefsEditor=pref1.edit();
        editor = pref.edit();
    }
    public void saveLatLng(String lat,String lng){
        editor.putString(KEY_LAT, lat);
        editor.putString(KEY_LNG, lng);
        editor.commit();
    }
    /**
     * Create login session
     * */
  /*  public void createLoginSession(String pass, String phone){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_PASSWORD, pass);

        // Storing email in pref
        editor.putString(KEY_PHONE, phone);


        // commit changes
        editor.commit();
    }*/


    public void createLoginSession( String phone){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref

        // Storing email in pref
        editor.putString(KEY_PHONE, phone);



        // commit changes
        editor.commit();
    }

    public void createLoginSession2( String name){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref

        // Storing email in pref

        editor.putString(KEY_NAME, name);


        // commit changes
        editor.commit();
    }



    public void createRegistrSession( String phone){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref


        // Storing email in pref
        editor.putString(KEY_PHONE, phone);


        // commit changes
        editor.commit();
    }

/////

    /**
     * Create remenberme session
     * */
    public void saveorderkey(String orderkey){


        // Storing name in pref
        loginPrefsEditor.putString(KEY_ORDERID, orderkey);


        // commit changes
        loginPrefsEditor.commit();
    }

    public void saveuserid(String userid){


        // Storing name in pref
        loginPrefsEditor.putString(KEY_USER_ID, userid);


        // commit changes
        loginPrefsEditor.commit();
    }

    public void saveorderdetails(String storename , String storeaddress,String customername,String customeraddress,String totalamount,String storelat,String storelang,String custlat,String custlang,String pickupId){
        // Storing name in pref
        loginPrefsEditor.putString(KEY_STORENAME, storename);
        loginPrefsEditor.putString(KEY_STOREADDRESS, storeaddress);
        loginPrefsEditor.putString(KEY_CUSTNAME, customername);
        loginPrefsEditor.putString(KEY_CUSTADDRESS, customeraddress);
        loginPrefsEditor.putString(KEYTOTALAMOUNT, totalamount);
        loginPrefsEditor.putString(KEY_STORELAT, storelat);
        loginPrefsEditor.putString(KEY_STORELANG, storelang);
        loginPrefsEditor.putString(KEY_CUSTLAT, custlat);
        loginPrefsEditor.putString(KEY_CUSTLANG, custlang);
        loginPrefsEditor.putString(KEY_PICKUPID, pickupId);
        // commit changes
        loginPrefsEditor.commit();
    }

    public void save_name( String phone){
        loginPrefsEditor.putString(KEY_PHONE, phone);
        // commit changes
        loginPrefsEditor.commit();
    }
    public void saveLangDetails(String details){


        // Storing name in pref
        loginPrefsEditor.putString(KEY_LANGUAGE_DETAILS, details);


        // commit changes
        loginPrefsEditor.commit();
    }

    public void saveDefaultAddress(String address, String address_id){


        // Storing name in pref
        loginPrefsEditor.putString(KEY_DEFAULT_ADDRESS, address);
        loginPrefsEditor.putString(KEY_DEFAULT_ADDRESS_ID, address_id);


        // commit changes
        loginPrefsEditor.commit();
    }

    public void save_Language(int code){

        // Storing name in pref
        loginPrefsEditor.putInt(KEY_LNG_SELECTED, code);


        // commit changes
        loginPrefsEditor.commit();
    }
    public void savelocation(String location) {

        loginPrefsEditor.putString(KEY_LOCATION, location);
        loginPrefsEditor.commit();
    }

    public void saveLanguage_name(String code){

        // Storing name in pref
        loginPrefsEditor.putString(KEY_LANGUAGE_NAME, code);


        // commit changes
        loginPrefsEditor.commit();
    }


    public int getLanguage(){


        //if language code not stored will return 1(English)
        int s1=pref.getInt(KEY_LNG_SELECTED, 1); // getting String

        return s1;
    }



    public void checkLogin(){
        // Check login status
        if(this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }

    }
    public void checkRegister(){
        // Check login status
        if(this.Register()){
            // user is not logged in redirect him to Login Activity

            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }
    }
    /**
     * Clear session details
     * */
    public void  logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);// for clearing
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public boolean Register(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public String checkRememberMe(String phone, String password){
        String rememberMe="uncheck";

        String userId=pref1.getString("phone1", null); // getting String
        String userpassword=pref1.getString("pass1", null); // getting String
        System.out.println("llllllllllllllllllll"+userId);
        System.out.println("llllllllllllllllllll"+userpassword);
        if (phone.equals(userId)){
            rememberMe=userpassword;
        }


        return rememberMe;
    }

    public String getRegId(String s2){

        String s1=pref.getString(s2, ""); // getting String

        return s1;
    }


}

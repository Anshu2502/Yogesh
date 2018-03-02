package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SplashScreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    int locationPermission, locationPermissions,locationPermissionsa,result;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String number;
    DateFormat inputFormat ;
    DateFormat outputFormat;
    String inputDateStr;
    Date date = null;
    LayoutInflater li;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);

        if(isConnectingToInternet()){



        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        String inputString1 = "2017-11-20";
        String inputString2 = thisDate;





        try {
            Date date1 = currentDate.parse(inputString1);
            Date date2 = currentDate.parse(inputString2);


           // if(date2.getTime()>date1.getTime()){
             //   Toast.makeText(this,"App Expires", Toast.LENGTH_SHORT).show();
            // }else{
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                        SharedPreferences prefs = getSharedPreferences("Crediantials", MODE_PRIVATE);
                        number = prefs.getString("number", "empty");

                        if(Build.VERSION.SDK_INT >=23) {
                            if (checkPermission()){
                                if(!number.equals("empty")){
                                    Intent ii = new Intent(SplashScreen.this,Main_Dashboard.class);
                                    startActivity(ii);
                                    finish();
                                }else{
                                    Intent ii = new Intent(SplashScreen.this,MainActivity.class);
                                    startActivity(ii);
                                    finish();
                                }


                            }else{
                                requestPermission();
                            }
                        }else{
                            if(!number.equals("empty")){
                                Intent ii = new Intent(SplashScreen.this,Main_Dashboard.class);
                                startActivity(ii);
                                finish();
                            }else{
                                Intent ii = new Intent(SplashScreen.this,MainActivity.class);
                                startActivity(ii);
                                finish();
                            }

                        }


                    }
                }, SPLASH_DISPLAY_LENGTH);
         //   }

            long diff = date2.getTime() - date1.getTime();

          //  Toast.makeText(this,String.valueOf(diff), Toast.LENGTH_SHORT).show();






        } catch (ParseException e) {
            e.printStackTrace();
        }
        }else{
            net_check();
        }

    }

    private boolean checkPermission() {
        locationPermissionsa = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE );
        result = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE );
        locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        locationPermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        return result == PackageManager.PERMISSION_GRANTED && locationPermission == PackageManager.PERMISSION_GRANTED && locationPermissions == PackageManager.PERMISSION_GRANTED;
    }

    private boolean requestPermission() {

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermissionsa != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (result != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (locationPermissions != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                if(!number.equals("empty")){
                    Intent ii = new Intent(SplashScreen.this,Main_Dashboard.class);
                    startActivity(ii);
                    finish();
                }else{
                    Intent ii = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(ii);
                    finish();
                }

            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }

    }


    public void net_check(){

        li = LayoutInflater.from(this);
        View confirmDialog = li.inflate(R.layout.dialog_internet, null);
        Button buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(confirmDialog);

        final AlertDialog alertDialog = alert.create();

        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);


        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();

            }
        });




    }




    public boolean isConnectingToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            System.out.println("Network"+
                                    "NETWORKNAME: " + anInfo.getTypeName());
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

















}
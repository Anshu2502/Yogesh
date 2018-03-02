package com.patients.ayushmaanbhava.ayushmaanbhavadoctorapp;

/**
 * Created by acer on 9/5/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SplashScreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    int locationPermission, locationPermissions,locationPermissionsa,result;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String number;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                SharedPreferences prefs = getSharedPreferences("Crediantial", MODE_PRIVATE);
                 number = prefs.getString("number", "empty");
                Toast.makeText(SplashScreen.this, number, Toast.LENGTH_SHORT).show();
                if(Build.VERSION.SDK_INT >=23) {
                    if (checkPermission()){
                        if(!number.equals("empty")){
                            Intent ii = new Intent(SplashScreen.this,MainActivity.class);
                            startActivity(ii);
                            finish();
                        }else{
                            Intent ii = new Intent(SplashScreen.this,Login.class);
                            startActivity(ii);
                            finish();
                        }

                    }else {
                        requestPermission();
                    }
                }else {
                    if(!number.equals("empty")){
                        Intent ii = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(ii);
                        finish();
                    }else{
                        Intent ii = new Intent(SplashScreen.this,Login.class);
                        startActivity(ii);
                        finish();
                    }
                }


                }

        }, SPLASH_DISPLAY_LENGTH);
    }

    private boolean checkPermission() {
        locationPermissionsa = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE );
        result = ContextCompat.checkSelfPermission(SplashScreen.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE );

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
                    Intent ii = new Intent(SplashScreen.this,MainActivity.class);
                    startActivity(ii);
                    finish();
                }else{
                    Intent ii = new Intent(SplashScreen.this,Login.class);
                    startActivity(ii);
                    finish();
                }

            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }

    }
}
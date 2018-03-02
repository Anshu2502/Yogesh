package com.patients.ayushmaanbhava.ayushmaanbhavaadminapp;

/**
 * Created by acer on 9/20/2017.
 */

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 24/2/17.
 */

public class Splash extends Activity {
    int locationPermission, locationPermissions,locationPermissionsa,result;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String mail,password;
    String s = "127Something73";
    int sum = 0;
    public  void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.splash);

/*

        for(int i=0;i < s.length();i++){

            if(Character.isDigit(s.charAt(i))){
                sum = sum + Integer.parseInt(s.charAt(i) + "");
            }

        }
        Toast.makeText(this, String.valueOf(sum), Toast.LENGTH_SHORT).show();

*/

/*        if(Build.VERSION.SDK_INT >=23) {
            if (checkPermission()){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent b = new Intent(Splash.this,MainActivity.class);
                        startActivity(b);
                        finish();
                    }
                }, 3000);


            }else{
                requestPermission();
            }
        }else{
 */           final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent b = new Intent(Splash.this,MainActivity.class);
                    startActivity(b);
                    finish();
                }
            }, 3000);

   //     }






/*
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                    Intent b = new Intent(Splash.this,MainActivity.class);
                    startActivity(b);
                    finish();
            }
        }, 3000);
*/

    }
/*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //  Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent b = new Intent(Splash.this,MainActivity.class);
                        startActivity(b);
                        finish();
                    }
                }, 3000);

            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }

    }
*/

    private boolean checkPermission() {
        locationPermissionsa = ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.READ_EXTERNAL_STORAGE );
        result = ContextCompat.checkSelfPermission(Splash.this, Manifest.permission.WRITE_EXTERNAL_STORAGE );

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


}

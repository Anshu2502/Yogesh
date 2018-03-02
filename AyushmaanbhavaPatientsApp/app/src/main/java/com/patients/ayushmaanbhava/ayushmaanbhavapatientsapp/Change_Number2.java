package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class Change_Number2 extends AppCompatActivity {

    String oldnumber,newnumber,otp;
    EditText new_number;
    Button change_number;
    String get_number;
    LayoutInflater li;
    EditText editTextConfirmOtp;
    private BroadcastReceiver mIntentReceiver;
    private RequestQueue requestQueue;

    public void onCreate(Bundle SavedInsatnceState){
        super.onCreate(SavedInsatnceState);
        setContentView(R.layout.change_number2);
        new_number = (EditText)findViewById(R.id.log);
        change_number = (Button)findViewById(R.id.buttoncheck);
        otp = ""+((int)(Math.random()*9000)+1000);
        requestQueue = Volley.newRequestQueue(Change_Number2.this);



        Intent op = getIntent();
        oldnumber = op.getStringExtra("old_number");
        Toast.makeText(this, oldnumber, Toast.LENGTH_SHORT).show();


    change_number.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            newnumber = new_number.getText().toString();
            if(newnumber.length()>=0){
                change_number();
            }else{
                Toast.makeText(Change_Number2.this, "Invalid Number", Toast.LENGTH_SHORT).show();
            }




           // Toast.makeText(Change_Number2.this, newnumber, Toast.LENGTH_SHORT).show();





        }
    });




    }






    public void loginn(){

        if(oldnumber.length()>=10){
            final ProgressDialog loading = ProgressDialog.show(Change_Number2.this, "Please Wait", "Changing Password", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/number_update.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("success")){
                                Toast.makeText(Change_Number2.this, "Number Changed Successfuly", Toast.LENGTH_SHORT).show();
                               Intent op = new Intent(Change_Number2.this,Login.class);
                                op.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(op);
                                loading.dismiss();
                            }else{
                                loading.dismiss();
                                Toast.makeText(Change_Number2.this,response,Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Change_Number2.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("newnumber",newnumber);
                    map.put("oldnumber",oldnumber);
                    map.put("otp",otp);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(this, "Please Input Correctly Values", Toast.LENGTH_SHORT).show();
        }



    }



    public void change_number(){

      //  get_number = f_password.getText().toString();

        if( newnumber!=null){
            final ProgressDialog loading = ProgressDialog.show(Change_Number2.this, "Please Wait", "Changing Number", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/number_change.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("success")){

                                try {
                                    loading.dismiss();
                                    confirmOtp();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }else{
                                loading.dismiss();
                                Toast.makeText(Change_Number2.this,response,Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Change_Number2.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("number",oldnumber);
                    map.put("newnumber",newnumber);
                    map.put("otp",otp);

                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(this, "Please Input Correctly Values", Toast.LENGTH_SHORT).show();
        }



    }


    private void confirmOtp() throws JSONException {
        li = LayoutInflater.from(Change_Number2.this);
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);
        Button buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);

        AlertDialog.Builder alert = new AlertDialog.Builder(Change_Number2.this);
        alert.setView(confirmDialog);

        final AlertDialog alertDialog = alert.create();

        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);

        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("get_msg");
                msg = msg.replace("\n", "");
                String body = msg.substring(msg.lastIndexOf(":")+1, msg.length());
                String pNumber = msg.substring(0,msg.lastIndexOf(":"));
                editTextConfirmOtp.setText(body);
            }
        };

        this.registerReceiver(mIntentReceiver, intentFilter);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alertDialog.dismiss();


                final ProgressDialog loading = ProgressDialog.show(Change_Number2.this, "Authenticating", "Please wait while we check the entered code", false, false);


                final String otp = editTextConfirmOtp.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/forgot_password2.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //  Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                if(response.equalsIgnoreCase("success")){


                                    loading.dismiss();

                                    loginn();
                                    //finish();



                                }else{


                                    try {

                                        confirmOtp();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                alertDialog.dismiss();

                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();

                        params.put("otp", otp);
                        params.put("number", oldnumber);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }




}

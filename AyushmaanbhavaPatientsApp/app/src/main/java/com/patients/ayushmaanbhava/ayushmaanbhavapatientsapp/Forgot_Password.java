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

public class Forgot_Password extends AppCompatActivity {
        EditText f_password;
        Button  check_number;
        String get_number,otp;
        LayoutInflater li;
        EditText editTextConfirmOtp;
        private BroadcastReceiver mIntentReceiver;
        private RequestQueue requestQueue;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.forgot_password);
        f_password = (EditText)findViewById(R.id.get_pass);
        check_number = (Button)findViewById(R.id.check);
        otp = ""+((int)(Math.random()*9000)+1000);
        requestQueue = Volley.newRequestQueue(Forgot_Password.this);

        check_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f_password.length()==0){
                    Toast.makeText(Forgot_Password.this, "Please Enter Number", Toast.LENGTH_SHORT).show();
                }else{
                    forgot_password();
                }
            }
        });



    }




    public void forgot_password(){

        get_number = f_password.getText().toString();

        if( get_number!=null){
            final ProgressDialog loading = ProgressDialog.show(Forgot_Password.this, "Please Wait", "Signing In", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/forgot_password1.php",
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
                                Toast.makeText(Forgot_Password.this,response,Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Forgot_Password.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("number",get_number);
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
        li = LayoutInflater.from(Forgot_Password.this);
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);
        Button buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);

        AlertDialog.Builder alert = new AlertDialog.Builder(Forgot_Password.this);
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


                final ProgressDialog loading = ProgressDialog.show(Forgot_Password.this, "Authenticating", "Please wait while we check the entered code", false, false);


                final String otp = editTextConfirmOtp.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/forgot_password2.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                              //  Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                if(response.equalsIgnoreCase("success")){


                                    loading.dismiss();

                                    Intent ii = new Intent(Forgot_Password.this, Forgot_Password2.class);
                                    ii.putExtra("number",get_number);
                                    startActivity(ii);
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
                        params.put("number", get_number);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }







}

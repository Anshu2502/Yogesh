package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;

public class Change_Number1 extends AppCompatActivity {
    EditText current_number, current_password;
    String g_number,g_password;
    Button buttoncheck;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.change_number1);

        current_number = (EditText)findViewById(R.id.log);
        current_password = (EditText)findViewById(R.id.password);
        buttoncheck = (Button)findViewById(R.id.buttoncheck);

        buttoncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(current_number.getText().toString().length()>=10&&current_password.getText().toString().length()>0){
                    loginn();
                }else{
                    Toast.makeText(Change_Number1.this, "Please Complete Form", Toast.LENGTH_SHORT).show();
                }

            }
        });











    }

    public void loginn(){

        g_number = current_number.getText().toString();
        g_password = current_password.getText().toString();

        if(g_number.length()>=10 && g_number!=null && g_password!=null){
            final ProgressDialog loading = ProgressDialog.show(Change_Number1.this, "Please Wait", "Signing In", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/login.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("success")){

                                Intent oo = new Intent(Change_Number1.this,Change_Number2.class);
                                oo.putExtra("old_number",g_number);
                                startActivity(oo);
                                finish();
                                loading.dismiss();
                            }else{
                                loading.dismiss();
                                Toast.makeText(Change_Number1.this,response,Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Change_Number1.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("username",g_number);
                    map.put("password",g_password);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }else {
            Toast.makeText(this, "Please Input Correctly Values", Toast.LENGTH_SHORT).show();
        }



    }

}

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
import java.util.Objects;


public class Forgot_Password2 extends AppCompatActivity {

    EditText p1,p2;
    Button change_password;
    String a,b,number;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.forgot_password2);

        Intent io = getIntent();
        number = io.getStringExtra("number");


        p1 = (EditText)findViewById(R.id.pass);
        p2 = (EditText)findViewById(R.id.confirm_pass);
        change_password = (Button)findViewById(R.id.check);



        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                a = p1.getText().toString();
                b = p2.getText().toString();


                if(Objects.equals(a, b)&&a.length()>0){

                    change_password();


                }




            }
        });


    }


    public void change_password(){



        if( number!=null){
            final ProgressDialog loading = ProgressDialog.show(Forgot_Password2.this, "Please Wait", "Changeing Password", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/forgot_password_update.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("success")){

                                loading.dismiss();
                                Toast.makeText(Forgot_Password2.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                Intent ip = new Intent(Forgot_Password2.this, Login.class);
                                startActivity(ip);
                                finish();

                            }else{
                                loading.dismiss();
                                Toast.makeText(Forgot_Password2.this,response,Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Forgot_Password2.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("number",number);
                    map.put("password",a);

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

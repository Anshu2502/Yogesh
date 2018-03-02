package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class Login extends AppCompatActivity {

    EditText p_number,p_password;
    String g_number,g_password;
    Button log_in;
    TextView forgot_pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        p_number = (EditText)findViewById(R.id.log);
        p_password = (EditText)findViewById(R.id.password);
        log_in = (Button)findViewById(R.id.buttonlogin);
        forgot_pass = (TextView)findViewById(R.id.forgot_password);

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent io = new Intent(Login.this, Forgot_Password.class);
                startActivity(io);
            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginn();
            }
        });
    }

    public void loginn(){

        g_number = p_number.getText().toString();
        g_password = p_password.getText().toString();

        if(g_number.length()>=10 && g_number!=null && g_password!=null){
            final ProgressDialog loading = ProgressDialog.show(Login.this, "Please Wait", "Signing In", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/login.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.trim().equals("success")){
                                loading.dismiss();
                                SharedPreferences.Editor editor = getSharedPreferences("Crediantials", MODE_PRIVATE).edit();
                                editor.putString("number", g_number);
                               // editor.putInt("idName", 12);
                                editor.apply();
                                Intent main = new Intent(Login.this,Main_Dashboard.class);
                                main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(main);
                                finish();

                            }else{
                                loading.dismiss();
                                Toast.makeText(Login.this,response,Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG ).show();
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

package com.patients.ayushmaanbhava.ayushmaanbhavadoctorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button appoint, leave, profile;
    String number,user_name, user_id, user_imgname, maill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Doctor App");
        appoint = (Button)findViewById(R.id.buttonappoint);
        leave = (Button)findViewById(R.id.buttonleave);
        profile = (Button)findViewById(R.id.buttonprofile);
        SharedPreferences prefs = getSharedPreferences("Crediantial", MODE_PRIVATE);
        number = prefs.getString("number", "empty");
        get_user_data();
        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ap = new Intent(MainActivity.this,Get_Appointment.class);
                startActivity(ap);
            }
        });

        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent le = new Intent(MainActivity.this,Doc_Leave.class);
                startActivity(le);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pr = new Intent(MainActivity.this,Profile.class);
                startActivity(pr);
            }
        });

    }

    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(MainActivity.this, "Please Wait", "Getting Profile Information", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/doctor_profile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject jsonObject=jArray.getJSONObject(i);

                                user_name = jsonObject.getString("dname");
                                user_id = jsonObject.getString("did");
                                user_imgname = jsonObject.getString("dimg");
                                maill = jsonObject.getString("demail");
                            }
                            SharedPreferences.Editor editor = getSharedPreferences("Crediantial", MODE_PRIVATE).edit();
                            editor.putString("doc_id", user_id);
                            // editor.putInt("idName", 12);
                            editor.apply();
                            Toast.makeText(MainActivity.this, user_id, Toast.LENGTH_SHORT).show();
                         //   initNavigationDrawer();

                        }


                        catch(JSONException e){
                            e.printStackTrace();
                        }

                           /* materialDesignSpinner.setItems(response);*/

                        Log.e("Data------->>>",response);
                        loading.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(), Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("number",number);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            SharedPreferences preferences = getSharedPreferences("Crediantial", 0);
            preferences.edit().remove("number").apply();
            Intent io = new Intent(MainActivity.this,Login.class);
            startActivity(io);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }











}

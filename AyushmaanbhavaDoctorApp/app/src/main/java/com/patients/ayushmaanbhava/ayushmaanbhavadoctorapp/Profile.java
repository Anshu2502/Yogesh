package com.patients.ayushmaanbhava.ayushmaanbhavadoctorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
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
import java.util.Objects;

/**
 * Created by acer on 10/20/2017.
 */

public class Profile extends AppCompatActivity {
    ImageView u_img;
    ImageLoader imgLoader;
    Bitmap bitmap;
    int loader = R.mipmap.patient;

    TextView dname,dmail,dnumber,dedu;

    String number,user_name, user_id, user_imgname, maill,utype,edu;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.profile);
        u_img = (ImageView)findViewById(R.id.imga);
        imgLoader = new ImageLoader(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("Crediantial", MODE_PRIVATE);
        number = prefs.getString("number", "empty");
        setTitle("Profile");
        dname = (TextView)findViewById(R.id.d_name);
      //  dtype = (TextView)findViewById(R.id.d_type);
        dmail = (TextView)findViewById(R.id.g_d_email);
        dnumber = (TextView)findViewById(R.id.d_number);
        dedu = (TextView)findViewById(R.id.d_edu);


        //  u_img = (ImageView)findViewById(R.id.imga);
        get_user_data();


    }


    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(Profile.this, "Please Wait", "Getting Profile Information", false, false);

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
                                utype=jsonObject.getString("etype");
                                edu = jsonObject.getString("dedu");
                            }
                            String im = "http://ayushmaanbhavahealingcenter.com/admin/adminassets/images/";
                            Toast.makeText(Profile.this, user_imgname, Toast.LENGTH_SHORT).show();
                             if(!Objects.equals(user_imgname,"")&&!Objects.equals(user_imgname, null)){
                                imgLoader.DisplayImage(im+user_imgname,loader , u_img);
                            }else {
                                imgLoader.DisplayImage("http://ayushmaanbhavahealingcenter.com/admin/adminassets/images/patient.png",loader , u_img);
                            }


                            dname.setText(user_name);
                            dmail.setText(maill);
                            dnumber.setText(number);
                          //  dtype.setText(utype);
                            dedu.setText(edu);


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
                        Toast.makeText(Profile.this,error.toString(), Toast.LENGTH_LONG ).show();
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
            Intent io = new Intent(Profile.this,Login.class);
            startActivity(io);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

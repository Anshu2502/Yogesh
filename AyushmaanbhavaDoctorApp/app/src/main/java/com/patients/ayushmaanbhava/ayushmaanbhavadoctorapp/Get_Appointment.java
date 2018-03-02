package com.patients.ayushmaanbhava.ayushmaanbhavadoctorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 10/20/2017.
 */

public class Get_Appointment extends AppCompatActivity {
    private RecyclerView mRVFishPrice;
    private Pro_Adapter mAdapter;
     String uid;
    DateFormat inputFormat ;
    DateFormat outputFormat;
    String inputDateStr;
    Date date = null;
    public  void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.appointment);
        setTitle("Appointment");
        SharedPreferences prefs = getSharedPreferences("Crediantial", MODE_PRIVATE);
        uid = prefs.getString("doc_id", "empty");
        inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        get_appoint_list();
     //   Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

    }

    public void get_appoint_list(){
        final ProgressDialog loading = ProgressDialog.show(Get_Appointment.this, "Please Wait", "Fetching Appointment List", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/doc_appoint_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(Appointments.this, response, Toast.LENGTH_SHORT).show();
                        List<Pro_Cons> data=new ArrayList<>();
                        try {
                            JSONArray jArray = new JSONArray(response);

                            for(int i=0;i<jArray.length();i++){
                                //appoint++;
                                JSONObject json_data = jArray.getJSONObject(i);
                                Pro_Cons fishData = new Pro_Cons();

                                date = inputFormat.parse(json_data.getString("adate"));

                                fishData.date = outputFormat.format(date);
                                fishData.id = json_data.getString("aid");

                                data.add(fishData);
                            }
                            mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                            mAdapter = new Pro_Adapter(Get_Appointment.this, data);
                            mRVFishPrice.setAdapter(mAdapter);
                            mRVFishPrice.setLayoutManager(new LinearLayoutManager(Get_Appointment.this));
                        }
                        catch (JSONException e){

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Get_Appointment.this,error.toString(), Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("did",uid);
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
            Intent io = new Intent(Get_Appointment.this,Login.class);
            startActivity(io);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}

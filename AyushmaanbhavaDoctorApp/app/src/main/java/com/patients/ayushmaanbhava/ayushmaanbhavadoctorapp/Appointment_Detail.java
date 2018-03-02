package com.patients.ayushmaanbhava.ayushmaanbhavadoctorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer on 10/20/2017.
 */

public class Appointment_Detail extends AppCompatActivity {
    String id,a_date,a_type,v_type,v_reason,note,pname,pnum;
    TextView adate,atype,vtype,vreason,vnote,patient_name,patient_number;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.appoint_detail);
        setTitle("Appointment");
        Intent io = getIntent();
        id = io.getStringExtra("appoint_id");

        patient_name = (TextView)findViewById(R.id.p_name);
        patient_number = (TextView)findViewById(R.id.p_number);
        adate = (TextView)findViewById(R.id.appoint_date);
        atype = (TextView)findViewById(R.id.appointtype);
        vtype = (TextView)findViewById(R.id.visittype);
        vreason = (TextView)findViewById(R.id.reason);
        vnote = (TextView)findViewById(R.id.p_note);

        get_appoint_list();
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

    }





    public void get_appoint_list(){
        final ProgressDialog loading = ProgressDialog.show(Appointment_Detail.this, "Please Wait", "Fetching Appointment Detail", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/doc_appoint_detail.php",
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
                               a_date = json_data.getString("adate");
                                a_type = json_data.getString("atype");
                                v_type = json_data.getString("vtype");
                                v_reason = json_data.getString("reason");
                                note = json_data.getString("rec_note");
                                pname = json_data.getString("name");
                                pnum = json_data.getString("phone1");


                            }


                            adate.setText(a_date);
                            atype.setText(a_type);
                            vtype.setText(v_type);
                            vreason.setText(v_reason);
                            vnote.setText(note);

                            patient_name.setText(pname);
                            patient_number.setText(pnum);

                     }
                        catch (JSONException e){

                        }
                       loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Appointment_Detail.this,error.toString(), Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("aid",id);
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
            Intent io = new Intent(Appointment_Detail.this,Login.class);
            startActivity(io);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

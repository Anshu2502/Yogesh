package com.patients.ayushmaanbhava.ayushmaanbhavadoctorapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by acer on 10/20/2017.
 */

public class Doc_Leave extends AppCompatActivity {
    ImageButton dobb;
    EditText st;
    String uid,datee;
    private int mYear, mMonth, mDay;
    Button notifyy;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.leave);
        setTitle("Doctor App");
        SharedPreferences prefs = getSharedPreferences("Crediantial", MODE_PRIVATE);
        uid = prefs.getString("doc_id", "empty");

        dobb = (ImageButton)findViewById(R.id.b_dob);
        st = (EditText)findViewById(R.id.dob);

        notifyy = (Button)findViewById(R.id.buttonleave);

        dobb.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(Doc_Leave.this,new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // g_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        st.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
});

        notifyy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datee = st.getText().toString();
                if(!Objects.equals(datee, "")&&!Objects.equals(datee, null)){

                    get_appoint_list();

                }else{
                    Toast.makeText(Doc_Leave.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


/*
    @Override
    public void onClick(View v) {
        if(v==dobb){
            Toast.makeText(this, "12", Toast.LENGTH_SHORT).show();

        }
    }
*/


    public void get_appoint_list(){

        datee= st.getText().toString();
        final ProgressDialog loading = ProgressDialog.show(Doc_Leave.this, "Please Wait", "Fetching Appointment Detail", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/insert_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("success")){

                            Toast.makeText(Doc_Leave.this, "Notified to Admin", Toast.LENGTH_SHORT).show();

                            loading.dismiss();
                        }else{
                            Toast.makeText(Doc_Leave.this, "Server Error", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Doc_Leave.this,error.toString(), Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("did",uid);
                map.put("date",datee);
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
            Intent io = new Intent(Doc_Leave.this,Login.class);
            startActivity(io);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

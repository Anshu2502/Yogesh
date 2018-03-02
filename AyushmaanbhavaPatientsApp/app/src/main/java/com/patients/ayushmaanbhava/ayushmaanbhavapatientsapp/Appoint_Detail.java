package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Created by acer on 9/14/2017.
 */

public class Appoint_Detail extends AppCompatActivity {
    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
    private RecyclerView mRVFishPrice;
    private Pro_Adapter2 mAdapter;
    String apoointment_id,patient_id,appoint_date,appoint_type,visit_type,visit_reson,all_test,test_one,test_two,test_three,test_four;
    TextView adate,atype,vtype,reason,no_test;
    Button cancel;
    long ab;
    SimpleDateFormat sdf;

    public void onCreate(Bundle SavedInstanceState){
       super.onCreate(SavedInstanceState);
        setContentView(R.layout.appointments);
        Intent intent = getIntent();
        apoointment_id = intent.getStringExtra("appoint_id");
        patient_id = intent.getStringExtra("patient_id");
        cancel = (Button)findViewById(R.id.cancel_now);
        adate = (TextView)findViewById(R.id.g_name);
        atype = (TextView)findViewById(R.id.g_number);
        vtype = (TextView)findViewById(R.id.g_email);
        reason = (TextView)findViewById(R.id.g_date);
        no_test = (TextView)findViewById(R.id.no_test_require);
         sdf = new SimpleDateFormat("yyyy-MM-dd");
        no_test.setVisibility(View.GONE);

        get_user_data();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cancel_appointment();


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Toast.makeText(Appoint_Detail.this, "Yes", Toast.LENGTH_SHORT).show();
                                cancel_appointment();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                               // Toast.makeText(Appoint_Detail.this, "No", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(Appoint_Detail.this);
                builder.setMessage("Cancellation Charge Wil Be 10% of Your Appointment Fee, Are You Sure You Want to Cancel??").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();






            }
        });





    }
    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(Appoint_Detail.this, "Please Wait", "Getting Appointment Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/appoint_get_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONArray jArray =new JSONArray(response);
                            if(jArray.length()>0){
                                for(int i=0;i<jArray.length();i++){
                                    JSONObject jsonObject=jArray.getJSONObject(i);
                                    appoint_date = jsonObject.getString("adate");
                                    appoint_type = jsonObject.getString("atype");
                                    visit_type = jsonObject.getString("vtype");
                                    visit_reson = jsonObject.getString("reason");
                                    all_test = jsonObject.getString("pre_tests");

                                    test_one = jsonObject.getString("ptest1");
                                    test_two = jsonObject.getString("ptest2");
                                    test_three = jsonObject.getString("ptest3");
                                    test_four = jsonObject.getString("ptest4");
                                }
                                adate.setText(appoint_date);
                                atype.setText(appoint_type);
                                vtype.setText(visit_type);
                                reason.setText(visit_reson);



                                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
                                Date todayDate = new Date();
                                String thisDate = currentDate.format(todayDate);

                                String inputString1 = appoint_date;
                                String inputString2 = thisDate;

                                try {
                                    Date date1 = currentDate.parse(inputString1);
                                    Date date2 = currentDate.parse(inputString2);
                                    long diff = date2.getTime() - date1.getTime();

                                    ab = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                                 //   Toast.makeText(Appoint_Detail.this, String.valueOf("val: "+ab), Toast.LENGTH_SHORT).show();


                                    //   Toast.makeText(Main_Dashboard.this,String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)), Toast.LENGTH_SHORT).show();

                                    System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                if(ab>=-1 &&ab<1 ){
                                    cancel.setEnabled(false);
                                    cancel.setText("Cannot Cancel Appointment");
                                }else if(ab>=1){
                                    cancel.setEnabled(false);
                                    cancel.setText("Appointment Completed");
                                }
                                else {
                                    cancel.setEnabled(true);
                                    cancel.setText("Cancel Now");
                                }


                                if(!Objects.equals(all_test, "")){

                                    get_user_data2();


                                }else {
                                    Toast.makeText(Appoint_Detail.this, "No Value", Toast.LENGTH_SHORT).show();
                                    no_test.setVisibility(View.VISIBLE);
                                }



                            }else{



                            }

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
                        Toast.makeText(Appoint_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("aid",apoointment_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void get_user_data2(){
        final ProgressDialog loading = ProgressDialog.show(Appoint_Detail.this, "Please Wait", "Getting Test Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/appoint_get_detail2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String test = response.replaceAll("\\[", "").replaceAll("\\]","");

                        String[] single_test = test.split(",");
                        List<Pro_Cons> data=new ArrayList<>();
                         for(String s_test:single_test){
                             String z_test=s_test.replace("\"", "");

                             String w_test =z_test.replaceAll("[\\s\\-()]", "");

                             String remove_num = w_test.replaceAll("[0-9]","");

                             String remove_word = w_test.replaceAll("[^0-9.]", "");
                             Pro_Cons fishData = new Pro_Cons();
                             fishData.test_id = remove_word;
                             fishData.patient_id = patient_id;
                             fishData.all_test = all_test;
                             fishData.appoint_id = apoointment_id;
                             fishData.test_name = remove_num;
                             data.add(fishData);
                         }
                        mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                        mAdapter = new Pro_Adapter2(Appoint_Detail.this, data);
                        mRVFishPrice.setAdapter(mAdapter);
                        mRVFishPrice.setLayoutManager(new LinearLayoutManager(Appoint_Detail.this));
                        loading.dismiss();

                        }
                          /* materialDesignSpinner.setItems(response);*/
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Appoint_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("aid",apoointment_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }



    public void cancel_appointment(){

        final ProgressDialog loading = ProgressDialog.show(Appoint_Detail.this, "Please Wait", "Cancelling Appointment", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/cancel_appointment.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            loading.dismiss();
                            Toast.makeText(Appoint_Detail.this, "Appointment Cancelled", Toast.LENGTH_SHORT).show();
                            Intent io = new Intent(Appoint_Detail.this,Appointments.class);
                            startActivity(io);
                            finish();


                        }else{
                            loading.dismiss();
                        }



                    }
                          /* materialDesignSpinner.setItems(response);*/
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Appoint_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("aid",apoointment_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);





    }


 }

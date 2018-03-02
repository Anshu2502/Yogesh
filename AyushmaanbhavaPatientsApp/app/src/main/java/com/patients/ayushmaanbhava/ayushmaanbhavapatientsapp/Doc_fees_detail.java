package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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


public class Doc_fees_detail extends AppCompatActivity {
String aid ;
    String apoointment_id,patient_id,appoint_date,appoint_type,visit_type,visit_reson,paystatus,pay_amount;
     TextView a_date,a_vtype,a_visit_amout,a_paymentstatus;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.fees_detail);

        Intent  ii = getIntent();
       aid =  ii.getStringExtra("appoint_id");
       a_date = (TextView)findViewById(R.id.g_date);
        a_vtype = (TextView)findViewById(R.id.visit_type);
        a_visit_amout =  (TextView)findViewById(R.id.pay_amount);
        a_paymentstatus = (TextView)findViewById(R.id.pay_status);


        get_user_data();
        Toast.makeText(this,aid, Toast.LENGTH_SHORT).show();

    }


    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(Doc_fees_detail.this, "Please Wait", "Getting Appointment Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/appoint_get_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject jsonObject=jArray.getJSONObject(i);
                                appoint_date = jsonObject.getString("adate");
                                appoint_type = jsonObject.getString("atype");
                                visit_type = jsonObject.getString("vtype");
                                paystatus = jsonObject.getString("pay_status");

                            }

                            a_date.setText(appoint_date);
                            a_vtype.setText(appoint_type);

                            if(Objects.equals(appoint_type, "Urgent Appointment")){
                               // Toast.makeText(Doc_fees_detail.this, appoint_type, Toast.LENGTH_SHORT).show();
                                pay_amount = "Rs.1500/-";
                                a_visit_amout.setText("Rs.1500/-");

                            }else{
                                pay_amount = "Rs.1000/-";
                                a_visit_amout.setText("Rs.1000/-");
                            }

                            if(Objects.equals(paystatus, "true")){
                                a_paymentstatus.setText("Payment Completed");
                            }else{
                                a_paymentstatus.setText("Payment Not Completed");
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
                        Toast.makeText(Doc_fees_detail.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("aid",aid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }




}

package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import java.util.Objects;

public class Prescription_detail_more extends AppCompatActivity {
    String apoointment_id,p_date,p_symtoms,p_advise,medicineget;
    TextView pdate,psymtom,padvise;
    String[] medicine;
    ListView mname,quantity;
    List<String> mediname,mquantity;
    ArrayAdapter<String> name_medi, quanti_medi;
 public void onCreate(Bundle SavedInstanceState){
     super.onCreate(SavedInstanceState);
     setContentView(R.layout.prescription_detail_more);
     Intent intent = getIntent();
     apoointment_id = intent.getStringExtra("appoint_id");
     pdate = (TextView)findViewById(R.id.g_date);
     psymtom = (TextView)findViewById(R.id.symtom);
     padvise = (TextView)findViewById(R.id.advise);
     mname = (ListView) findViewById(R.id.m_name);
     quantity = (ListView)findViewById(R.id.m_quantity);
     mediname = new ArrayList<String>();
     mquantity = new ArrayList<String>();

    // Toast.makeText(this, apoointment_id, Toast.LENGTH_SHORT).show();
     if(!Objects.equals(apoointment_id, null)){
         get_user_data();
     }

 }


    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(Prescription_detail_more.this, "Please Wait", "Getting Appointment Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/prescription_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(Prescription_detail_more.this, response, Toast.LENGTH_SHORT).show();
                        try{
                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject jsonObject=jArray.getJSONObject(i);
                                p_date = jsonObject.getString("p_date");
                                p_symtoms = jsonObject.getString("symptoms");
                                p_advise = jsonObject.getString("advise");


                            }

                            pdate.setText(p_date);
                            psymtom.setText(p_symtoms);
                            padvise.setText(p_advise);

                            get_user_data_2();

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
                        Toast.makeText(Prescription_detail_more.this,error.toString(),Toast.LENGTH_LONG ).show();
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


    public void get_user_data_2(){

        final ProgressDialog loading = ProgressDialog.show(Prescription_detail_more.this, "Please Wait", "Getting Medicine Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/prescription_medicine.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         // Toast.makeText(Prescription_detail_more.this, response, Toast.LENGTH_SHORT).show();
                            medicineget = response.replaceAll("\\[", "").replaceAll("\\]","");

                            medicine = medicineget.split(",");
                       for(String medicine_fullname:medicine){

                           String medicinequantity = medicine_fullname.replaceAll(".*-", "");

                           int i =  medicine_fullname.indexOf('Q');
                           String medicinename = medicine_fullname.substring(0,i);

                           String medi_name = medicinename.replace("\"", "");
                           String medi_quantity = medicinequantity.replace("\"", "");

                           String w_test =medi_name.replaceAll("[\\s\\-()]", "");

                           String remove_word = medi_quantity.replaceAll("[^0-9.]", "");
                         //  Toast.makeText(Prescription_detail_more.this, w_test, Toast.LENGTH_SHORT).show();
                            mediname.add(w_test);
                            mquantity.add(remove_word);



                           //Toast.makeText(Prescription_detail_more.this, medi_name+" - "+medi_quantity, Toast.LENGTH_SHORT).show();


                       }
                       if(mediname.size()>0 && mquantity.size()>0){
                           aa();
                       }

                           /* materialDesignSpinner.setItems(response);*/

                        Log.e("Data------->>>",response);
                        loading.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Prescription_detail_more.this,error.toString(),Toast.LENGTH_LONG ).show();
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

public void aa(){
    name_medi = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mediname);
    quanti_medi = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mquantity);
    mname.setAdapter(name_medi);
    quantity.setAdapter(quanti_medi);
}


}

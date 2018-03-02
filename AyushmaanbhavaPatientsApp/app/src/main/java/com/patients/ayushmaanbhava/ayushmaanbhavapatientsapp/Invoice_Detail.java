package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Invoice_Detail extends AppCompatActivity {

    private RecyclerView mRVFishPrice;
    private Pro_Adapter6 mAdapter;
    double total;
    String apoointment_id,appoint_date,appoint_type,visit_type,visit_reson,medicine_quantity,address,city,state,pincode,awb_number
            ,total_subamount,parcel_amount,final_total,is_parcel,parcel_phone,pay_status,number,user_name,user_id,user_imgname,
            user_email,user_number,street1,city1,state1,zip1,phone1,p,aa,bb,ttal_gst;
    TextView adate,atype,vtype,reason,no_test,p_number,p_address,p_subamount,p_isparcel,p_parcel_amount,p_final_amount,awbb
            ,u_sgst,u_cgst,u_igst;
    Pro_Cons fishData;
    List<Pro_Cons> data;
    Button pay;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.invoice_detail);
        Intent intent = getIntent();
        apoointment_id = intent.getStringExtra("appoint_id");
        adate = (TextView)findViewById(R.id.g_name);
        atype = (TextView)findViewById(R.id.g_number);
        vtype = (TextView)findViewById(R.id.g_email);
        reason = (TextView)findViewById(R.id.g_date);

        u_sgst = (TextView)findViewById(R.id.sgct);
        u_cgst = (TextView)findViewById(R.id.cgct);
        u_igst = (TextView)findViewById(R.id.igct);

        no_test = (TextView)findViewById(R.id.no_test_require);
        pay = (Button)findViewById(R.id.pay_now);
       // pay.setEnabled(false);
        no_test.setVisibility(View.GONE);

        SharedPreferences prefs = getSharedPreferences("Crediantials", MODE_PRIVATE);
        number = prefs.getString("number", "empty");

        p_number = (TextView)findViewById(R.id.pnumber);
        awbb = (TextView)findViewById(R.id.awb);

        p_address = (TextView)findViewById(R.id.address);
        p_subamount = (TextView)findViewById(R.id.p_subamount);
        p_isparcel = (TextView)findViewById(R.id.isparcel);
        p_parcel_amount = (TextView)findViewById(R.id.par_amount);
        p_final_amount = (TextView)findViewById(R.id.finaltotal);

        data=new ArrayList<>();

        get_user_data();

      //  float correct = 25;
        double questionNum = 100;
        double percent = (13 * 200.0f) / questionNum;

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_user_data_main();

            }
        });

    }

    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(Invoice_Detail.this, "Please Wait", "Getting Appointment Details", false, false);

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
                                visit_reson = jsonObject.getString("reason");
                               // inmed = jsonObject.getString("in_meds");

                            }
                            adate.setText(appoint_date);
                            atype.setText(appoint_type);
                            vtype.setText(visit_type);
                            reason.setText(visit_reson);


                              //  get_user_data2();

                           // if(!Objects.equals(inmed, "")){

                                get_user_data3();


                          //  }else {
                          //      Toast.makeText(Invoice_Detail.this, "No Value", Toast.LENGTH_SHORT).show();
                         //       no_test.setVisibility(View.VISIBLE);
                           // }


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
                        Toast.makeText(Invoice_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
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
        final ProgressDialog loading = ProgressDialog.show(Invoice_Detail.this, "Please Wait", "Getting Appointment Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/invoice.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        String[] medicine = response.split(",");

                   //     Toast.makeText(Invoice_Detail.this, response, Toast.LENGTH_SHORT).show();

                        Log.e("<<<response>>>>",response);
                 //       ["Arsenic Alb 200 \/ Qyt:1 - Price:100.00 @ 2","Rhus Tox 200 \/ Qyt:2 - Price:100.00 @ 4","Pulsatilla 200 \/ Qyt:2 - Price:100.00 @ 6"]








                        for(String s_test:medicine){
                            String z_test=s_test.replace("\"", "");

                           // System.out.print(s_test.split("@")[1] + " : ");



                            //Toast.makeText(Invoice_Detail.this, p, Toast.LENGTH_SHORT).show();

                            String medicinequantity2 = z_test.replaceAll(".*/", "");



                            int indexOfDash = medicinequantity2.indexOf('-');
                            String before = medicinequantity2.substring(0, indexOfDash);
                            //Toast.makeText(Invoice_Detail.this, before, Toast.LENGTH_SHORT).show();

                            String medicinequantity = z_test.replaceAll(".*-", "");
                            int i =  z_test.indexOf('Q');
                            String medicinename = z_test.substring(0,i);

                            medicinename = medicinename.replaceAll("\\[|\\]", "");

                            medicinename = medicinename.replaceAll("[\\s\\-()]", "");

                            medicinename=medicinename.replaceAll("\\/","");

                            String medi_price = medicinequantity.replace("\"", "");

                            medi_price = medi_price.replaceAll("\\[|\\]", "");


                            medi_price = medi_price.substring(1, medi_price.indexOf("@"));

                           // Toast.makeText(Invoice_Detail.this, medi_price, Toast.LENGTH_SHORT).show();

                            p = s_test.split("@")[1].replaceAll("\\[|\\]", "").replaceAll("^\"|\"$", "");
                             aa = medi_price.replaceAll("[^\\d.]", "");
                             bb = before.replaceAll("[^\\d.]", "");

                         //  Toast.makeText(Invoice_Detail.this, before.replaceAll("[^\\d.]", ""), Toast.LENGTH_SHORT).show();

                            fishData = new Pro_Cons();

                            fishData.med_name = medicinename;
                            fishData.med_gst = p+"%";
                            fishData.med_final_price = "Rs."+cal_percent(p,aa,bb);
                            fishData.med_price = "Rs."+medi_price.replaceAll("[^\\d.]", "");;
                            fishData.med_quantity = before.replaceAll("[^\\d.]", "");
                            data.add(fishData);

                            total+= Double.parseDouble(cal_percent(p,aa,bb));
                           // Toast.makeText(Invoice_Detail.this, String.valueOf(total), Toast.LENGTH_SHORT).show();

                        }

                        mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                        mAdapter = new Pro_Adapter6(Invoice_Detail.this, data);
                        mRVFishPrice.setAdapter(mAdapter);
                        mRVFishPrice.setLayoutManager(new LinearLayoutManager(Invoice_Detail.this));


                        if(Objects.equals(state, "Madhya Pradesh")||Objects.equals(state1, "Madhya Pradesh")){

                            String one = dividenum(ttal_gst);
                            String two = dividenum(ttal_gst);
                            u_sgst.setText(one);
                            u_cgst.setText(two);
                            u_igst.setText("0%");


                        }else{
                            u_sgst.setText("0%");
                            u_cgst.setText("0%");
                            u_igst.setText(ttal_gst);
                        }








                        p_subamount.setText(String.valueOf(total));


                        if(Objects.equals(is_parcel, "yes")){
                            p_isparcel.setText(is_parcel);
                            p_parcel_amount.setText(parcel_amount);

                            double i = Double.parseDouble(String.valueOf(total))+Double.parseDouble(parcel_amount);
                            total = i;
                            p_final_amount.setText(String.valueOf(i));
                        }else {
                            p_isparcel.setText("No");
                            p_parcel_amount.setText("0");
                            p_final_amount.setText(String.valueOf(total));
                        }

                        if(!Objects.equals(pay_status, "true")){
                            pay.setEnabled(true);
                            pay.setText("Pay Now");
                        }else {
                            pay.setEnabled(false);
                            pay.setText("Payment Completed");
                        }

                        awbb.setText(awb_number);






                           /* materialDesignSpinner.setItems(response);*/

                        Log.e("Data------->>>",response);
                        loading.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Invoice_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
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

    public void get_user_data3(){
        final ProgressDialog loading = ProgressDialog.show(Invoice_Detail.this, "Please Wait", "Getting Appointment Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/invoice2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try{
                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++) {
                                JSONObject jsonObject=jArray.getJSONObject(i);


                                address = jsonObject.getString("parcel_address");
                                city = jsonObject.getString("p_city");
                                state = jsonObject.getString("p_state");
                                pincode = jsonObject.getString("p_zip");
                                awb_number = jsonObject.getString("awb_number");
                                total_subamount = jsonObject.getString("in_subtotal");
                                is_parcel = jsonObject.getString("is_parcel");
                                parcel_amount = jsonObject.getString("in_parcel");
                                final_total = jsonObject.getString("in_total");
                                parcel_phone = jsonObject.getString("parcel_phone");
                                ttal_gst = jsonObject.getString("gst_total");
                                pay_status = jsonObject.getString("pstatus");

                               }
                          //  Toast.makeText(Invoice_Detail.this, parcel_amount, Toast.LENGTH_SHORT).show();
                            if(!Objects.equals(parcel_phone, "")){
                                p_number.setText(parcel_phone);
                            }else{
                                p_number.setText(phone1);
                            }


                            if(!Objects.equals(address, "")&&!Objects.equals(city, "")&&!Objects.equals(state, "")&&!Objects.equals(pincode, "")){
                              //  Toast.makeText(Invoice_Detail.this, "1a", Toast.LENGTH_SHORT).show();
                                p_address.setText(address +","+city+","+state+","+pincode);

                            }else{
                            //    Toast.makeText(Invoice_Detail.this, "2a", Toast.LENGTH_SHORT).show();
                               //street1,city1,state1,zip1

                                get_user_data_main2();
                            }




                           // String mq[] = medicine_quantity.split(",");

                           /* for(String m_q :mq){
                                fishData = new Pro_Cons();
                                fishData.med_quantity = m_q;
                                data.add(fishData);
                            }*/

                            get_user_data2();





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
                        Toast.makeText(Invoice_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
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

    public void get_user_data_main(){
        final ProgressDialog loading = ProgressDialog.show(Invoice_Detail.this, "Please Wait", "Payment ", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/get_user_profile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject jsonObject=jArray.getJSONObject(i);

                                user_name = jsonObject.getString("name");
                                user_id = jsonObject.getString("id");
                                user_imgname = jsonObject.getString("img");
                                user_email = jsonObject.getString("email");
                                user_number = jsonObject.getString("phone1");

                                street1 = jsonObject.getString("street");
                                city1 = jsonObject.getString("city");
                                state1 = jsonObject.getString("state");
                                zip1 = jsonObject.getString("zip");
                            }


                            Intent io = new Intent(Invoice_Detail.this,Payu_Test2.class);
                            io.putExtra("amount",String.valueOf(total));
                            io.putExtra("names",user_name);
                            io.putExtra("id",user_id);
                            io.putExtra("appointmentid",apoointment_id);
                            io.putExtra("mail",user_email);
                            io.putExtra("number",user_number);
                            startActivity(io);
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
                        Toast.makeText(Invoice_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
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


    public String cal_percent(String no,String tot,String h){

        double d=Double.parseDouble(no);
        double a = Double.parseDouble(tot);
        double n = Double.parseDouble(h);

       // d=((double)d/a) * 100;

        double questionNum = 100;
        d = (d * a) / questionNum;

        double q= d+a;
        double r = n*q;
        String o = String.valueOf(r);

        return o;
      //  Toast.makeText(this, String.valueOf(d), Toast.LENGTH_SHORT).show();



    }




    public void get_user_data_main2(){
        final ProgressDialog loading = ProgressDialog.show(Invoice_Detail.this, "Please Wait", "Geeting Data ", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/get_user_profile.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject jsonObject=jArray.getJSONObject(i);

                                street1 = jsonObject.getString("street");
                                city1 = jsonObject.getString("city");
                                state1 = jsonObject.getString("state");
                                zip1 = jsonObject.getString("zip");
                                phone1 = jsonObject.getString("phone1");
                            }
                         //   Toast.makeText(Invoice_Detail.this, "Street "+street1, Toast.LENGTH_SHORT).show();
                            p_address.setText(street1 +","+city1+","+state1+","+zip1);


                         //   Toast.makeText(Invoice_Detail.this, street1, Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(Invoice_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
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

           public String dividenum(String numm){

               float numm2 = Float.parseFloat(numm);

               float numm3 = numm2/2;



               return String.valueOf(numm3);
           }













    public void set_appoint() {

        final ProgressDialog loading = ProgressDialog.show(Invoice_Detail.this, "Please Wait", "Medicine Payment", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/medicine_payment_update.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("success")) {
                         //   pstatus.setText("Payment Completed");




                            Log.e("Data------->>>", response);
                            loading.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Invoice_Detail.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("aid", apoointment_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }










}

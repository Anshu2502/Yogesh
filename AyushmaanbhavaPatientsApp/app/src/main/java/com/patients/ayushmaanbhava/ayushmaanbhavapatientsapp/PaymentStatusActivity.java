package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by acer on 9/20/2017.
 */

public class PaymentStatusActivity extends Activity {
String txid, idd,u_name,u_mail,u_number,u_appoint_type,u_visit_type,u_reason,datee,doc_id;
    boolean p_status;
    Button bb;
    TextView pstatus,txn,adate,atype,avisit,uname,umail,unumber;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.paymentstatusactivity);

        Intent i = getIntent();
         txid = i.getStringExtra("transaction_id");
         idd = i.getStringExtra("id");

        bb = (Button)findViewById(R.id.done);
        u_name = i.getStringExtra("name");
        u_mail = i.getStringExtra("email");
        u_number = i.getStringExtra("number");
        u_appoint_type = i.getStringExtra("a_type");
        u_visit_type = i.getStringExtra("v_type");
        u_reason = i.getStringExtra("reason");
        datee = i.getStringExtra("datee");
        doc_id = i.getStringExtra("did");
        p_status = i.getExtras().getBoolean("status");

        pstatus = (TextView)findViewById(R.id.pstatuss);
        txn = (TextView)findViewById(R.id.u_txn);
        adate = (TextView)findViewById(R.id.a_date);
        atype = (TextView)findViewById(R.id.u_appoint_type);
        avisit = (TextView)findViewById(R.id.u_visit_type);
        //uidd = (TextView)findViewById(R.id.u_id);
        uname = (TextView)findViewById(R.id.u_name);
        unumber = (TextView)findViewById(R.id.u_number);
        umail = (TextView)findViewById(R.id.u_mail);


        if(p_status){
            set_appoint();
          /*pstatus.setText("Payment Completed");
            pstatus.setTextColor(Color.GREEN);
            txn.setText(txid);
            uidd.setText(idd);
            uname.setText(u_name);
            unumber.setText(u_number);
            umail.setText(u_mail);
            atype.setText(u_appoint_type);
            avisit.setText(u_visit_type);
            adate.setText(datee);*/

        }else {
            pstatus.setText("Payment Failed");
            pstatus.setTextColor(Color.RED);



            txn.setText(txid);
            //uidd.setText(idd);
            uname.setText(u_name);
            unumber.setText(u_number);
            umail.setText(u_mail);
            atype.setText(u_appoint_type);
            avisit.setText(u_visit_type);
            adate.setText(datee);
        }


         bb.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent io = new Intent(PaymentStatusActivity.this,Main_Dashboard.class);
                 io.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 io.putExtra("EXIT", true);
                 startActivity(io);
                 finish();
             }
         });


/*
        Toast.makeText(this, u_name, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, u_mail, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, u_number, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, u_appoint_type, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, u_visit_type, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, u_reason, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, datee, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, txid, Toast.LENGTH_SHORT).show();
*/



    }

    public void set_appoint() {

        final ProgressDialog loading = ProgressDialog.show(PaymentStatusActivity.this, "Please Wait", "Setting Your Appointment", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/appoint_date.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("success")) {
                            pstatus.setText("Payment Completed");
                            pstatus.setTextColor(ContextCompat.getColor(PaymentStatusActivity.this, R.color.colorPrimary));
                            txn.setText(txid);
                           // uidd.setText(idd);
                            uname.setText(u_name);
                            unumber.setText(u_number);
                            umail.setText(u_mail);
                            atype.setText(u_appoint_type);
                            avisit.setText(u_visit_type);
                            adate.setText(datee);
                            Toast.makeText(PaymentStatusActivity.this, "Your Appointment Set Successfully", Toast.LENGTH_SHORT).show();

                            Log.e("Data------->>>", response);
                            loading.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentStatusActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("atype", u_appoint_type);
                map.put("adate", datee);
                map.put("vtype", u_visit_type);
                map.put("did", doc_id);
                map.put("pid", idd);
                map.put("reason", u_reason);
                map.put("pay_status", "true");
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}

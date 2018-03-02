package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

/**
 * Created by acer on 10/11/2017.
 */

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

public class PaymentStatusActivity2 extends Activity {
    String txid, idd,u_name,u_mail,u_number,u_appoint_type,u_visit_type,u_reason,datee,doc_id,amount,appoint_id;
    boolean p_status;
    Button bb;
    TextView pstatus,txn,adate,atype,avisit,uname,umail,unumber,utotal;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.paymentstatusactivity2);

        Intent i = getIntent();
        txid = i.getStringExtra("transaction_id");
        idd = i.getStringExtra("id");

        bb = (Button)findViewById(R.id.done);
        u_name = i.getStringExtra("name");
        u_mail = i.getStringExtra("email");
        u_number = i.getStringExtra("number");
        amount = i.getStringExtra("amount");
        appoint_id = i.getStringExtra("appoint_id");
        p_status = i.getExtras().getBoolean("status");

        utotal = (TextView)findViewById(R.id.u_total);
        pstatus = (TextView)findViewById(R.id.pstatuss);
        txn = (TextView)findViewById(R.id.u_txn);
        adate = (TextView)findViewById(R.id.a_date);

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
                Intent io = new Intent(PaymentStatusActivity2.this,Main_Dashboard.class);
                io.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                io.putExtra("EXIT", true);
                startActivity(io);
                finish();
            }
        });


        Toast.makeText(this, appoint_id, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, u_mail, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, u_number, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, txid, Toast.LENGTH_SHORT).show();



    }

    public void set_appoint() {

        final ProgressDialog loading = ProgressDialog.show(PaymentStatusActivity2.this, "Please Wait", "Medicine Payment", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/medicine_payment_update.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.trim().equals("success")) {
                            pstatus.setText("Payment Completed");
                            pstatus.setTextColor(ContextCompat.getColor(PaymentStatusActivity2.this, R.color.colorPrimary));
                            txn.setText(txid);

                            uname.setText(u_name);
                            unumber.setText(u_number);
                            umail.setText(u_mail);
                            utotal.setText(amount);
                            Toast.makeText(PaymentStatusActivity2.this, "Payment Completed", Toast.LENGTH_SHORT).show();

                            Log.e("Data------->>>", response);
                            loading.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PaymentStatusActivity2.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("aid", appoint_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
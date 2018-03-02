package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Chat extends AppCompatActivity {
    String number,user_name, user_id, user_imgname,admin_id,patient_id,admin_msg,patient_msg,filename,appoint_date;
    private DrawerLayout drawerLayout;
    int loader = R.mipmap.patient;
    private Toolbar toolbar;
    ListView list;
    Button send;
    EditText sendtext;
    ListAdapter adapter;
    ArrayList<HashMap<String, String>> personList;
    SimpleDateFormat timeStampFormat;
    long ab;
    TextView datesss,datee;
    DateFormat inputFormat ;
    DateFormat outputFormat;
    String inputDateStr;
    Date date = null;
    private static final String TAG_ID = "mess";
    private static final String TAG_MESSAGE = "message";
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.chat);
        inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        outputFormat = new SimpleDateFormat("dd-MMM-yyyy");
        datesss = (TextView)findViewById(R.id.datess);
        datee = (TextView)findViewById(R.id.datees);
        datesss.setVisibility(View.GONE);
        send = (Button) findViewById(R.id.btnSend);
        sendtext = (EditText) findViewById(R.id.inputMsg);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String,String>>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        SharedPreferences prefs = getSharedPreferences("Crediantials", MODE_PRIVATE);
        number = prefs.getString("number", "empty");
        setTitle("Chat");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        get_user_data();

         timeStampFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date myDate = new Date();
        filename = timeStampFormat.format(myDate);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insert();

            }
        });


    }

/*
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

            SharedPreferences preferences = getSharedPreferences("Crediantials", 0);
            preferences.edit().remove("number").apply();
            Intent io = new Intent(Chat.this,MainActivity.class);
            startActivity(io);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/


    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(Chat.this, "Please Wait", "Getting Profile Information", false, false);

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
                            }
                            SharedPreferences.Editor editor = getSharedPreferences("Crediantials", MODE_PRIVATE).edit();
                            editor.putString("user_id", user_id);
                            // editor.putInt("idName", 12);
                            editor.apply();

                            initNavigationDrawer();

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
                        Toast.makeText(Chat.this,error.toString(),Toast.LENGTH_LONG ).show();
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


    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        Intent home = new Intent(Chat.this,Main_Dashboard.class);
                        startActivity(home);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        Intent profile = new Intent(Chat.this,Profile_Select.class);
                        startActivity(profile);
                        break;
                    case R.id.appointment:
                        Intent appoinment = new Intent(Chat.this,Appointments.class);
                        startActivity(appoinment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.prescript_detail:
                        Intent prescription = new Intent(Chat.this,Prescription_Detail.class);
                        startActivity(prescription);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.blog:
                        Intent blog = new Intent(Chat.this,Blog.class);
                        startActivity(blog);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.invoice:
                        Intent invoice = new Intent(Chat.this,Fees.class);
                        startActivity(invoice);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.enquiry:
                        Intent enquiry = new Intent(Chat.this,Chat.class);
                        startActivity(enquiry);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        SharedPreferences preferences = getSharedPreferences("Crediantials", 0);
                        preferences.edit().remove("number").apply();
                        Intent io = new Intent(Chat.this,MainActivity.class);
                        io.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        io.putExtra("EXIT", true);
                        startActivity(io);
                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView tv_email = (TextView)header.findViewById(R.id.tv_email);
        ImageView mm = (ImageView) header.findViewById(R.id.imgg);
        ImageLoader imgLoader = new ImageLoader(getApplicationContext());
        String im = "http://ayushmaanbhavahealingcenter.com/admin/adminassets/images/";
        Toast.makeText(this, user_imgname, Toast.LENGTH_SHORT).show();
        if(!Objects.equals(user_imgname,"")&&!Objects.equals(user_imgname, null)){
            imgLoader.DisplayImage(im+user_imgname,loader , mm);
        }else {
            imgLoader.DisplayImage("http://ayushmaanbhavahealingcenter.com/admin/adminassets/images/patient.png",loader , mm);
        }


        if(!Objects.equals(user_name, "")&&!Objects.equals(user_name, null) ){
            tv_email.setText(user_name);
        }else {
            tv_email.setText("User Name");
        }

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


       get_chat_list();

    }

    public void get_chat_list(){
        final ProgressDialog loading = ProgressDialog.show(Chat.this, "Please Wait", "Getting Chat Information", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/chat.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(Chat.this, response, Toast.LENGTH_SHORT).show();
                        try{

                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                HashMap<String,String> persons = new HashMap<String,String>();
                                JSONObject jsonObject=jArray.getJSONObject(i);

                                admin_id = jsonObject.getString("aid");
                                patient_id = jsonObject.getString("pid");
                                admin_msg = jsonObject.getString("reply");
                                patient_msg = jsonObject.getString("enq");

                                persons.put(TAG_MESSAGE, admin_msg);
                                persons.put(TAG_ID, patient_msg);
                                personList.add(persons);

                                adapter = new SimpleAdapter(Chat.this, personList,
                                        R.layout.activity_message,
                                        new String[]{TAG_ID,  TAG_MESSAGE },
                                        new int[]{ R.id.id, R.id.messag});

                                list.setAdapter(adapter);
                                get_date();
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
                        Toast.makeText(Chat.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("pid",user_id);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    public void insert(){


            final ProgressDialog loading = ProgressDialog.show(Chat.this, "Please Wait", "Getting Chat Information", false, false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/chat_insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //  Toast.makeText(Chat.this, response, Toast.LENGTH_SHORT).show();
                            if(response.trim().equals("success")){

                                reload();
                            }

                           /* materialDesignSpinner.setItems(response);*/

                            Log.e("Data------->>>",response);
                            loading.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Chat.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> map = new HashMap<String,String>();
                    map.put("pid",user_id);
                    map.put("aid","0");
                    map.put("enq",sendtext.getText().toString());
                    map.put("eqdate",filename);
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);








    }

    public void get_date(){

        //Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();

        final ProgressDialog loading = ProgressDialog.show(Chat.this, "Please Wait", "Getting Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/qnuiry_date.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject jsonObject=jArray.getJSONObject(i);

                                appoint_date = jsonObject.getString("eqdate");

                            }
                            // Toast.makeText(Main_Dashboard.this, appoint_date, Toast.LENGTH_SHORT).show();
                            loading.dismiss();

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
                              //  Toast.makeText(Chat.this, String.valueOf("val: "+ab+"- "+appoint_date), Toast.LENGTH_SHORT).show();


                                //   Toast.makeText(Main_Dashboard.this,String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)), Toast.LENGTH_SHORT).show();

                                System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            if(ab>7){

                                send.setEnabled(true);

                                //fishData.date = outputFormat.format(date);
                                datesss.setVisibility(View.GONE);

                            }else{
                                datesss.setVisibility(View.VISIBLE);
                                try {
                                    date = inputFormat.parse(appoint_date);
                                  //  datee.setText("Last Date"+outputFormat.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                send.setEnabled(false);
                                // Toast.makeText(Main_Dashboard.this, "Next Appointment Will set after 15 days of previous appointment", Toast.LENGTH_SHORT).show();
                            }


//                                Toast.makeText(Main_Dashboard.this, thisDate, Toast.LENGTH_SHORT).show();

                                /*SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
                                Date todayDate = new Date();
                                String thisDate = currentDate.format(todayDate);*/










                        }


                        catch(JSONException e){
                            e.printStackTrace();
                            loading.dismiss();
                        }


                        loading.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Chat.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("pid",user_id);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }






}


package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class Main_Dashboard extends AppCompatActivity implements View.OnClickListener {


    String[] SPINNERLIST = {"Select","General Appointment -  1000/-", "Urgent Appointment -  1500/-"};
    String[] SPINNERLIST2 = {"Select","First Visit", "Follow Up"};
    String[] SPINNERLIST3 = {"Select","Dr. Yogesh Kushwah-", "Dr. Deepika Jain","Dr. Pooja Singh"};
    ImageButton dob;
    Button appoint;
    int loader = R.mipmap.patient;
    private int mYear, mMonth, mDay,a;
    String type,visit_types,doc,number,user_name,doc_id,datee,user_id,user_imgname,user_email,user_number,appoint_date;
    int rate;
    long ab;
    EditText g_dob,notes;
    ArrayList<String> listItems ;
    ArrayAdapter<String> adapter = null;
    ArrayList<String> list;
    ArrayList<String> list2;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    MaterialSpinner materialDesignSpinner;
    MaterialSpinner materialDesignSpinner2;
    MaterialSpinner materialDesignSpinner3;
    TextView datesss;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.main_dashboard);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
         SharedPreferences prefs = getSharedPreferences("Crediantials", MODE_PRIVATE);
         number = prefs.getString("number", "empty");
         setTitle("Book Appointment");
         appoint = (Button)findViewById(R.id.buttonappoint);
        datesss = (TextView)findViewById(R.id.datess);
        datesss.setVisibility(View.GONE);
        notes = (EditText)findViewById(R.id.note);
        dob = (ImageButton)findViewById(R.id.b_dob);
        dob.setEnabled(false);
        g_dob=(EditText)findViewById(R.id.dob);
        g_dob.setEnabled(false);
        dob.setOnClickListener(this);
        get_user_data();
        get_doc();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        //initNavigationDrawer();
        materialDesignSpinner = (MaterialSpinner)findViewById(R.id.android_material_design_spinner);

         materialDesignSpinner2 = (MaterialSpinner)findViewById(R.id.visit_type);
         materialDesignSpinner3 = (MaterialSpinner)findViewById(R.id.select_doc);



        materialDesignSpinner.setItems(SPINNERLIST);
        materialDesignSpinner2.setItems(SPINNERLIST2);
       
        materialDesignSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                type = item;
                dob.setEnabled(true);

            }
        });
        materialDesignSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });

        materialDesignSpinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                visit_types = item;

            }
        });
        materialDesignSpinner2.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });

        materialDesignSpinner3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                 doc_id = list2.get(position);
               // Toast.makeText(Main_Dashboard.this, doc_id, Toast.LENGTH_SHORT).show();
                doc = item;

            }
        });
        materialDesignSpinner3.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });


/*
      appoint.setText("Disabled");
        appoint.setEnabled(false);
*/

        appoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!Objects.equals(doc, "Select")&&!Objects.equals(type, "Select")&&!Objects.equals(type, null)&&!Objects.equals(visit_types, "Select")&&!Objects.equals(visit_types, null)&&!Objects.equals(doc_id, "")&&!Objects.equals(doc_id, null)&& notes.length()>0){

                    set_appoint();
                }else {
                    Toast.makeText(Main_Dashboard.this, "Please Complete This Form", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public void onClick(View view) {
        if(view==dob){

            if(type.equals("General Appointment -  1000/-")&&!type.equals("Select")){

                rate=7;
            }else{

                rate=3;
            }

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


             c.add(Calendar.DATE, +rate);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                           // Toast.makeText(Main_Dashboard.this, String.valueOf(year + "-" + (monthOfYear + 1) + "-" + view.getDayOfMonth()), Toast.LENGTH_SHORT).show();
                            // g_dob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            g_dob.setText(year + "-" + (monthOfYear + 1) + "-" +  view.getDayOfMonth());
                            datee = year + "-" + (monthOfYear + 1) + "-" +  view.getDayOfMonth();
                           // Toast.makeText(Main_Dashboard.this, datee, Toast.LENGTH_SHORT).show();
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        }
    }

    public void get_doc(){
         listItems =new ArrayList<>();
         list=new ArrayList<>();
         list2=new ArrayList<>();
            final ProgressDialog loading = ProgressDialog.show(Main_Dashboard.this, "Please Wait", "Getting Doctor's", false, false);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/get_doc.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            listItems.add("Select");
                            list2.add("11");
                            try{
                                JSONArray jArray =new JSONArray(response);
                                for(int i=0;i<jArray.length();i++){
                                    JSONObject jsonObject=jArray.getJSONObject(i);
                                   list.add(jsonObject.getString("dep_name"));
                                   list2.add(jsonObject.getString("depid"));


                                }
                            }
                            catch(JSONException e){
                                e.printStackTrace();
                            }

                                      listItems.addAll(list);
                                      materialDesignSpinner3.setItems(listItems);
                           /* materialDesignSpinner.setItems(response);*/

                                Log.e("Data------->>>",response);
                                loading.dismiss();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Main_Dashboard.this,error.toString(),Toast.LENGTH_LONG ).show();
                        }
                    }){

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);



    }


          public void get_user_data(){
              final ProgressDialog loading = ProgressDialog.show(Main_Dashboard.this, "Please Wait", "Getting Profile Information", false, false);

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
                              Toast.makeText(Main_Dashboard.this,error.toString(),Toast.LENGTH_LONG ).show();
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


    public void set_appoint(){

            String w_test =type.replaceAll("[\\s\\-()]", "");
            String remove_word = w_test.replaceAll("[^0-9.]", "");
              String remove_num = w_test.replaceAll("[0-9]","");

              if(Objects.equals(remove_num, "GeneralAppointment/")){
                  remove_num = "General Appointment";
              }else  if(Objects.equals(remove_num, "UrgentAppointment/")){
                  remove_num = "Urgent Appointment";
              }

            Intent io = new Intent(Main_Dashboard.this,Payu_Test.class);
            io.putExtra("amount",remove_word);
            io.putExtra("names",user_name);
            io.putExtra("id",user_id);
            io.putExtra("mail",user_email);
            io.putExtra("number",user_number);
            io.putExtra("a_date",datee);
            io.putExtra("appoint_type",remove_num);
            io.putExtra("v_type",visit_types);
            io.putExtra("doc_id",doc_id);
            io.putExtra("visit_reason",notes.getText().toString());
            startActivity(io);

 /*       //   Toast.makeText(this, remove_num, Toast.LENGTH_SHORT).show();

        final ProgressDialog loading = ProgressDialog.show(Main_Dashboard.this, "Please Wait", "Setting Your Appointment", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/appoint_date.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equals("success")){
                            Toast.makeText(Main_Dashboard.this, "Your Appointment Set Successfully", Toast.LENGTH_SHORT).show();
                           // materialDesignSpinner.setItems(response);

                            String w_test =type.replaceAll("[\\s\\-()]", "");
                            String remove_word = w_test.replaceAll("[^0-9.]", "");

                            Intent io = new Intent(Main_Dashboard.this,SimplePaymentPage.class);
                            io.putExtra("amount",remove_word);
                            io.putExtra("type","main");
                            startActivity(io);
                            finish();


                        Log.e("Data------->>>",response);
                        loading.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_Dashboard.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("atype",type);
                map.put("adate",datee);
                map.put("vtype",visit_types);
                map.put("did",doc_id);
                map.put("pid",user_id);
                map.put("pay_status", "false");
                map.put("reason", notes.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        */

    }


    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        Intent home = new Intent(Main_Dashboard.this,Main_Dashboard.class);
                        startActivity(home);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        Intent profile = new Intent(Main_Dashboard.this,Profile_Select.class);
                        startActivity(profile);
                        break;
                    case R.id.appointment:
                        Intent appoinment = new Intent(Main_Dashboard.this,Appointments.class);
                        startActivity(appoinment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.prescript_detail:
                        Intent prescription = new Intent(Main_Dashboard.this,Prescription_Detail.class);
                        startActivity(prescription);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.blog:
                        Intent blog = new Intent(Main_Dashboard.this,Blog.class);
                        startActivity(blog);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.invoice:
                        Intent invoice = new Intent(Main_Dashboard.this,Fees.class);
                        startActivity(invoice);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.enquiry:
                        Intent enquiry = new Intent(Main_Dashboard.this,Chat.class);
                        startActivity(enquiry);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        SharedPreferences preferences = getSharedPreferences("Crediantials", 0);
                        preferences.edit().remove("number").apply();
                        Intent io = new Intent(Main_Dashboard.this,MainActivity.class);
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
        //Toast.makeText(this, user_imgname, Toast.LENGTH_SHORT).show();
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
        get_date();
    }



    public void get_date(){

        //Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();

        final ProgressDialog loading = ProgressDialog.show(Main_Dashboard.this, "Please Wait", "Getting Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/appoint_date_limit.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            try{
                                JSONArray jArray =new JSONArray(response);
                                for(int i=0;i<jArray.length();i++){
                                    JSONObject jsonObject=jArray.getJSONObject(i);

                                    appoint_date = jsonObject.getString("adate");

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
                                   // Toast.makeText(Main_Dashboard.this, String.valueOf("val: "+ab+"- "+appoint_date), Toast.LENGTH_SHORT).show();


                                 //   Toast.makeText(Main_Dashboard.this,String.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)), Toast.LENGTH_SHORT).show();

                                    System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                                if(ab>15){

                                    appoint.setEnabled(true);
                                    datesss.setVisibility(View.GONE);

                                }else{
                                    datesss.setVisibility(View.VISIBLE);
                                    appoint.setText("Disabled");
                                    appoint.setEnabled(false);
                                    // Toast.makeText(Main_Dashboard.this, "Next Appointment Will set after 15 days of previous appointment", Toast.LENGTH_SHORT).show();
                                }


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
                        Toast.makeText(Main_Dashboard.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("number",user_id);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}

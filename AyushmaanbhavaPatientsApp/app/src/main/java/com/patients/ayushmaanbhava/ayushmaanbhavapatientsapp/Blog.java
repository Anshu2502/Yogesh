package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Blog extends AppCompatActivity {
    String number,user_name,user_imgname,user_id;
    private DrawerLayout drawerLayout;
    int loader = R.mipmap.patient;
    private Toolbar toolbar;
    private RecyclerView mRVFishPrice;
    private Pro_Adapter4 mAdapter;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.blog);

        SharedPreferences prefs = getSharedPreferences("Crediantials", MODE_PRIVATE);
        number = prefs.getString("number", "empty");
        setTitle("Information");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


        get_user_data();

    }

    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(Blog.this, "Please Wait", "Getting Profile Information", false, false);

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
                        Toast.makeText(Blog.this,error.toString(),Toast.LENGTH_LONG ).show();
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



    public void get_appoint_list(){
        final ProgressDialog loading = ProgressDialog.show(Blog.this, "Please Wait", "Fetching Appointment List", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/blog_list.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(Appointments.this, response, Toast.LENGTH_SHORT).show();
                        List<Pro_Cons> data=new ArrayList<>();
                        try {
                            JSONArray jArray = new JSONArray(response);

                            for(int i=0;i<jArray.length();i++){

                                JSONObject json_data = jArray.getJSONObject(i);
                                Pro_Cons fishData = new Pro_Cons();
                                fishData.blog_id = json_data.getString("blog_id");
                                fishData.bolg_name = json_data.getString("blog_title");
                                fishData.blog_date = json_data.getString("blog_date");
                                fishData.imagev = json_data.getString("blog_img");


                                data.add(fishData);
                            }
                            mRVFishPrice = (RecyclerView)findViewById(R.id.fishPriceList);
                            mAdapter = new Pro_Adapter4(Blog.this, data);
                            mRVFishPrice.setAdapter(mAdapter);
                            mRVFishPrice.setLayoutManager(new LinearLayoutManager(Blog.this));
                        }
                        catch (JSONException e){

                        }
                        loading.dismiss();



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Blog.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();

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
                        Intent home = new Intent(Blog.this,Main_Dashboard.class);
                        startActivity(home);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        Intent profile = new Intent(Blog.this,Profile_Select.class);
                        startActivity(profile);
                        break;
                    case R.id.appointment:
                        Intent appoinment = new Intent(Blog.this,Appointments.class);
                        startActivity(appoinment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.prescript_detail:
                        Intent prescription = new Intent(Blog.this,Prescription_Detail.class);
                        startActivity(prescription);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.blog:
                        Intent blog = new Intent(Blog.this,Blog.class);
                        startActivity(blog);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.enquiry:
                        Intent enquiry = new Intent(Blog.this,Chat.class);
                        startActivity(enquiry);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.invoice:
                        Intent invoice = new Intent(Blog.this,Fees.class);
                        startActivity(invoice);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        SharedPreferences preferences = getSharedPreferences("Crediantials", 0);
                        preferences.edit().remove("number").apply();
                        Intent io = new Intent(Blog.this,MainActivity.class);
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


        get_appoint_list();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_logout) {
//
//            SharedPreferences preferences = getSharedPreferences("Crediantials", 0);
//            preferences.edit().remove("number").apply();
//            Intent io = new Intent(Blog.this,MainActivity.class);
//            startActivity(io);
//            finish();
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

}

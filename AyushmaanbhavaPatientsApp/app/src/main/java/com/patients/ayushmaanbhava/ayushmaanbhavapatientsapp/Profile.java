package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class Profile extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    ImageView u_img;
    ImageLoader imgLoader;
    ArrayList<String> listItems ;
    EditText mail,uname,uaddress,cpassword,password,current_password,u_street,u_city,u_landmark,u_pincode;
    String user_imgname,user_name,number,user_id,maill,file_name,imgg,ustreet,ustate,ucity,upincode,ulandmark;
    Button update_now;
    Bitmap bitmap;
    Uri filePath;
    TextView imggg;
    MaterialSpinner materialDesignSpinner3;
    String[] SPINNERLIST = {"Andaman and Nicobar Islands","Andhra Pradesh",
            "Arunachal Pradesh"
            ,"Assam",
            "Bihar",
            "Chandigarh",
            "Chhattisgarh",
            "Dadra and Nagar Haveli union territory",
            "Daman and Diu",
            "National Capital Territory of Delhi",
            "Goa",
            "Gujarat",
            "Haryana",
            "Himachal Pradesh",
            "Jammu and Kashmir",
            "Jharkhand",
            "Karnataka",
            "Kerala",
            "Lakshadweep",
            "Madhya Pradesh",
            "Maharashtra",
            "Manipur",
            "Meghalaya",
            "Mizoram",
            "Nagaland",
            "Odisha",
            "Puducherry ",
            "Punjab",
            "Rajasthan",
            "Sikkim",
            "Tamil Nadu",
            "Telangana",
            "Tripura",
            "Uttar Pradesh",
            "Uttarakhand",
            "West Bengal"};
    String user_naame,user_street,user_city,user_state,user_landmark,user_pincode;
    private static final int SELECT_PICTURE = 100;
    int loader = R.mipmap.patient;
    int locationPermission, locationPermissions,locationPermissionsa,result;
    private static final int PERMISSION_REQUEST_CODE = 1;

    public void onCreate(Bundle SaveInstance){
        super.onCreate(SaveInstance);
        setContentView(R.layout.profile2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setTitle("Profile");
        materialDesignSpinner3 = (MaterialSpinner)findViewById(R.id.visit_type);
        uname = (EditText)findViewById(R.id.user_name);
        u_street = (EditText)findViewById(R.id.street);
        u_city = (EditText)findViewById(R.id.city);
        u_landmark = (EditText)findViewById(R.id.landmark);
        u_pincode = (EditText)findViewById(R.id.pincode);



        u_img = (ImageView)findViewById(R.id.imga);
        mail = (EditText)findViewById(R.id.cmail);
        imggg = (TextView)findViewById(R.id.img_update);
        imggg.setPaintFlags(imggg.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        update_now = (Button)findViewById(R.id.submit);
        imgLoader = new ImageLoader(getApplicationContext());
        SharedPreferences prefs = getSharedPreferences("Crediantials", MODE_PRIVATE);
        number = prefs.getString("number", "empty");



        materialDesignSpinner3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

ustate = item;

            //    Toast.makeText(Profile.this, ustate, Toast.LENGTH_SHORT).show();

            }
        });







        get_user_data();





        imggg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(imgg, null)&&!Objects.equals(file_name, null)){

                    password_change();

              //      Toast.makeText(Profile.this, "Selected", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Profile.this, "Please  Selecte Image", Toast.LENGTH_SHORT).show();
                }
            }
        });










        u_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >=23) {
                    if (checkPermission()){
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);


                    }else{
                        requestPermission();
                    }
                }else{
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

                }




            }
        });


        update_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//maill,ustreet,ustate,ucity,upincode,ulandmark



                user_naame = uname.getText().toString();
                user_street = u_street.getText().toString();
                user_city = u_city.getText().toString();
                user_state = ustate;
                user_landmark = u_landmark.getText().toString();
                user_pincode = u_pincode.getText().toString();

                profile_update();






            }
        });



    }


    private boolean checkPermission() {
        locationPermissionsa = ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.READ_EXTERNAL_STORAGE );
        result = ContextCompat.checkSelfPermission(Profile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE );
        locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        locationPermissions = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        return result == PackageManager.PERMISSION_GRANTED && locationPermission == PackageManager.PERMISSION_GRANTED && locationPermissions == PackageManager.PERMISSION_GRANTED;
    }

    private boolean requestPermission() {

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationPermissionsa != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (result != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (locationPermissions != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


              //  Toast.makeText(this, "Permission Checked", Toast.LENGTH_SHORT).show();

            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }

    }



    public void get_user_data(){
        listItems =new ArrayList<>();
        final ProgressDialog loading = ProgressDialog.show(Profile.this, "Please Wait", "Getting Profile Information", false, false);

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
                                maill = jsonObject.getString("email");

                                ustreet = jsonObject.getString("street");
                                ucity = jsonObject.getString("city");
                                ustate = jsonObject.getString("state");
                                upincode = jsonObject.getString("zip");
                                ulandmark = jsonObject.getString("landmark");

                            }

                            uname.setText(user_name);
                            u_street.setText(ustreet);
                            u_city.setText(ucity);
                            u_pincode.setText(upincode);
                            u_landmark.setText(ulandmark);

                            listItems.add(ustate);
                            Collections.addAll(listItems, SPINNERLIST);

                            materialDesignSpinner3.setItems(listItems);
                            uname.setEnabled(false);

                            initNavigationDrawer();
                            String im = "http://ayushmaanbhavahealingcenter.com/admin/adminassets/images/";
                            if(!Objects.equals(user_imgname,"")&&!Objects.equals(user_imgname, null)){
                                imgLoader.DisplayImage(im+user_imgname,loader , u_img);
                            }else {
                                imgLoader.DisplayImage("http://ayushmaanbhavahealingcenter.com/admin/adminassets/images/patient.png",loader , u_img);
                            }

                            if(!Objects.equals(maill,"")&&!Objects.equals(maill, null)){
                               mail.setText(maill);
                               // mail.setEnabled(false);
                            }else {
                                mail.setText("User Mail Not Defined");
                               // mail.setEnabled(false);
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
                        Toast.makeText(Profile.this,error.toString(),Toast.LENGTH_LONG ).show();
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


    public void password_change(){
      final  String no= number;
       // final String n2 = current_password.getText().toString();
       // final String n3 = cpassword.getText().toString();

      //  Toast.makeText(this, no, Toast.LENGTH_SHORT).show();

        final ProgressDialog loading = ProgressDialog.show(Profile.this, "Please Wait", "Changing Credentials", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/password_change.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // Toast.makeText(Profile.this, response, Toast.LENGTH_SHORT).show();
                        if(response.trim().equals("success")){
                            loading.dismiss();
                            Intent intent = getIntent();
                            overridePendingTransition(0, 0);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                            Toast.makeText(Profile.this, "Credentials Change Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            loading.dismiss();
                            Toast.makeText(Profile.this, response, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Profile.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("number",no);
                //map.put("current_password",n2);
                //map.put("password",n3);
                map.put("email",mail.getText().toString());

                if(imgg!=null){
                    map.put("img",imgg);
                    map.put("img_name",file_name);
                }


                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                filePath = data.getData();
                if (null != filePath) {
                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                        bitmap = Bitmap.createScaledBitmap(bitmap, 960, 960, true);
                        // img.setImageBitmap(bitmap);

                        if (filePath.getScheme().equals("content")) {
                            try (Cursor cursor = getContentResolver().query(filePath, null, null, null, null)) {
                                if (cursor != null && cursor.moveToFirst()) {
                                    file_name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    //     Toast.makeText(this, file_name, Toast.LENGTH_SHORT).show();
                                   // img_name.setText(file_name);
                                    u_img.setImageBitmap(bitmap);

                                    imgg= getStringImage(bitmap);

                                }
                            }
                        }else {

                            String path= data.getData().getPath();
                            file_name=path.substring(path.lastIndexOf("/")+1);
                            u_img.setImageBitmap(bitmap);
                            imgg= getStringImage(bitmap);
                           // img_name.setText(file_name);
                            //    Toast.makeText(this, file_name, Toast.LENGTH_SHORT).show();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        Intent home = new Intent(Profile.this,Main_Dashboard.class);
                        startActivity(home);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.profile:
                        Intent profile = new Intent(Profile.this,Profile_Select.class);
                        startActivity(profile);
                        break;
                    case R.id.appointment:
                        Intent appoinment = new Intent(Profile.this,Appointments.class);
                        startActivity(appoinment);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.prescript_detail:
                        Intent prescription = new Intent(Profile.this,Prescription_Detail.class);
                        startActivity(prescription);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.blog:
                        Intent blog = new Intent(Profile.this,Blog.class);
                        startActivity(blog);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.enquiry:
                        Intent enquiry = new Intent(Profile.this,Chat.class);
                        startActivity(enquiry);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.invoice:
                        Intent invoice = new Intent(Profile.this,Fees.class);
                        startActivity(invoice);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        SharedPreferences preferences = getSharedPreferences("Crediantials", 0);
                        preferences.edit().remove("number").apply();
                        Intent io = new Intent(Profile.this,MainActivity.class);
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
    //    Toast.makeText(this, user_imgname, Toast.LENGTH_SHORT).show();
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
    }

  /*  @Override
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
            Intent io = new Intent(Profile.this,MainActivity.class);
            startActivity(io);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    public String getStringImage(Bitmap bmp){

        if(bmp!=null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }

        return null;
    }


    public void profile_update(){




        final  String no= number;
        final ProgressDialog loading = ProgressDialog.show(Profile.this, "Please Wait", "Changing Credentials", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/profile_update.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Toast.makeText(Profile.this, response, Toast.LENGTH_SHORT).show();
                        if(response.trim().equals("success")){
                            loading.dismiss();
                            Intent intent = getIntent();
                            overridePendingTransition(0, 0);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(intent);
                            Toast.makeText(Profile.this, "Credentials Change Successfully", Toast.LENGTH_SHORT).show();
                        }else{
                            loading.dismiss();
                            Log.e("<<>>",response);
                            Toast.makeText(Profile.this, response, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Profile.this,error.toString(),Toast.LENGTH_LONG ).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("number",no);
                //String user_naame,user_street,user_city,user_state,user_landmark,user_pincode;

                map.put("name",user_naame);
                map.put("street",user_street);
                map.put("city",user_city);
                map.put("state",user_state);
                map.put("landmark",user_landmark);
                map.put("pincode",user_pincode);
                map.put("mail",mail.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }



}

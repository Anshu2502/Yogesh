package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Image_upload extends AppCompatActivity {
    String listString = "",listString_pic = "",patient_id,appoint_id,test_id,all_test;
    int locationPermission, locationPermissions,locationPermissionsa,result;
    Button select_img,send_img;
    TextView text;
    ImageView img,img2;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int SELECT_PICTURE = 100;
    private Uri filePath;
    String file_name;
    private Bitmap bitmap;
    ArrayList<String> img_name;
    ArrayList<String> img_pic;
    public void onCreate(Bundle SavedInstance){
        super.onCreate(SavedInstance);
        setContentView(R.layout.img_upload);
        Intent intents = getIntent();

        patient_id = intents.getStringExtra("patient_id");
        appoint_id = intents.getStringExtra("appoint_id");
        test_id = intents.getStringExtra("test_id");
        all_test = intents.getStringExtra("all_test");

        Toast.makeText(this, all_test+" - "+appoint_id+" - "+ test_id, Toast.LENGTH_SHORT).show();



         img_name = new ArrayList<String>();
         img_pic = new ArrayList<String>();

        if(Build.VERSION.SDK_INT >=23) {
            if (checkPermission()){

                Intent intent = new Intent();
                intent.setType("image/*");


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "android.intent.action.SEND_MULTIPLE"), SELECT_PICTURE);
            }else{
                requestPermission();
            }
        }else{
            Intent intent = new Intent();
            intent.setType("image/*");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "android.intent.action.SEND_MULTIPLE"), SELECT_PICTURE);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (requestCode == SELECT_PICTURE) {

            if (resultCode == RESULT_OK) {
                //data.getParcelableArrayExtra(name);
                //If Single image selected then it will fetch from Gallery
                filePath = data.getData();
                if (data.getData() != null) {

                    filePath = data.getData();
                    if (null != filePath) {
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                            bitmap =  Bitmap.createScaledBitmap(bitmap, 960, 960, true);
                          //  img.setImageBitmap(bitmap);
                            if (filePath.getScheme().equals("content")) {
                                Cursor cursor = getContentResolver().query(filePath, null, null, null, null);
                                try {
                                    if (cursor != null && cursor.moveToFirst()) {
                                        file_name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                       // text.setText(file_name+",");
                                        if(img_name.size()<=3){
                                            img_name.add(file_name);
                                            img_pic.add(getStringImage(bitmap));
                                        }else {
                                            Toast.makeText(this, "Select only three Images", Toast.LENGTH_SHORT).show();
                                        }
                                        //      Toast.makeText(this, "1." + file_name, Toast.LENGTH_SHORT).show();
                                    }
                                } finally {
                                    cursor.close();
                                }
                            } else {

                                String path = data.getData().getPath();
                                file_name = path.substring(path.lastIndexOf("/") + 1);
                               // text.setText(file_name);
                                if(img_name.size()<=3){
                                    img_name.add(file_name);
                                    img_pic.add(getStringImage(bitmap));
                                }
                                //Toast.makeText(this, "2." + file_name, Toast.LENGTH_SHORT).show();
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {


                    //   }

                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();

                        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            try {

                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();
                                mArrayUri.add(uri);
                                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                                bitmap =  Bitmap.createScaledBitmap(bitmap, 960, 960, true);
                               // img.setImageBitmap(bitmap);
                                if (uri.getScheme().equals("content")) {
                                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                                    try {
                                        if (cursor != null && cursor.moveToFirst()) {
                                            file_name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                     //       text.setText(file_name+",");
                                            if(img_name.size()<=3){
                                                img_name.add(file_name);
                                                img_pic.add(getStringImage(bitmap));
                                            }
                                            //   Log.v("LOG_TAG", "Selected Images"+file_name);
                                            //   Toast.makeText(this, "2." + file_name, Toast.LENGTH_SHORT).show();
                                        }
                                    } finally {
                                        cursor.close();
                                    }
                                } else {

                                    String path =uri.getPath();// data.getData().getPath();

                                    file_name = path.substring(path.lastIndexOf("/") + 1);
                                    // text.setText(file_name);

                                    if(img_name.size()<=3){
                                        img_name.add(file_name);
                                        img_pic.add(getStringImage(bitmap));
                                    }


                                   // Toast.makeText(this, "2." + file_name, Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }


                }
                // img_name.add(file_name + ",");


                    /*for(String a : img_name){
                        listString += a + "\t";

                    }*/

                if(img_name.size()<=3){
                    final String imgname = Arrays.toString(img_name.toArray());
                    final String immpic = Arrays.toString(img_pic.toArray());

                    Log.v("LOG_TAG", "Selected Images 2 " + listString);

                    listString = imgname.replaceAll("\\[", "").replaceAll("\\]","");

                    listString_pic = immpic.replaceAll("\\[", "").replaceAll("\\]","");

                    if(listString!=null && listString_pic!=null){
                        Log.v("<<<img>>>",listString_pic);

                        Toast.makeText(this, listString, Toast.LENGTH_SHORT).show();

                        final ProgressDialog loading = ProgressDialog.show(Image_upload.this, "Please Wait", "Sending Your Report", false, false);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/report.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(response.trim().equals("success")){

                                           // Toast.makeText(this, listString_pic, Toast.LENGTH_LONG).show();
                                            img_name.removeAll(img_name);
                                            img_pic.removeAll(img_pic);
                                            finish();
                                            loading.dismiss();
                                        }else{
                                            img_name.removeAll(img_name);
                                            img_pic.removeAll(img_pic);
                                            finish();
                                            loading.dismiss();
                                            Toast.makeText(Image_upload.this,response,Toast.LENGTH_LONG).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(Image_upload.this,error.toString(),Toast.LENGTH_LONG ).show();
                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String,String> map = new HashMap<String,String>();
                                map.put("patient_id",patient_id);
                                map.put("appoint_id",appoint_id);
                                map.put("test_id",test_id);
                                map.put("image_name",imgname);
                                map.put("image",immpic);
                                map.put("all_test",all_test);
                                return map;
                            }
                        };

                        RequestQueue requestQueue = Volley.newRequestQueue(this);
                        requestQueue.add(stringRequest);



                    }else{
                        finish();
                    }
                }else{
                    Toast.makeText(this, "Select Only Three Image", Toast.LENGTH_LONG).show();
                    finish();
                }



            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    public String getStringImage(Bitmap bmp){

        if(bmp!=null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }

        return null;
    }
    private boolean checkPermission() {
        locationPermissionsa = ContextCompat.checkSelfPermission(Image_upload.this, Manifest.permission.READ_EXTERNAL_STORAGE );
        result = ContextCompat.checkSelfPermission(Image_upload.this, Manifest.permission.WRITE_EXTERNAL_STORAGE );
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

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }

    }
}

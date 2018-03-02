package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Blog_Detail extends AppCompatActivity {
    String blogid,blogdetail,blogimg;
    TextView blog_t;
    ImageView imv;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.blog_detail);
        Intent intent = getIntent();
        blogid = intent.getStringExtra("blog_id");
        blog_t = (TextView)findViewById(R.id.blog_detail);
        imv = (ImageView)findViewById(R.id.bimg);
        get_user_data();
    }

    public void get_user_data(){
        final ProgressDialog loading = ProgressDialog.show(Blog_Detail.this, "Please Wait", "Getting Blog Details", false, false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/blog_detail.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONArray jArray =new JSONArray(response);
                            for(int i=0;i<jArray.length();i++){
                                JSONObject jsonObject=jArray.getJSONObject(i);
                                blogdetail = jsonObject.getString("blog");
                                blogimg = jsonObject.getString("blog_img");

                            }
                            blog_t.setText(removeHtml(blogdetail));
                            Picasso.with(Blog_Detail.this)
                                    .load("http://ayushmaanbhavahealingcenter.com/admin/adminassets/images/"+blogimg)
                                    .placeholder(R.mipmap.patient)        //this is also optional if some error has occurred in downloading the image this image would be displayed
                                    .into(imv);


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
                        Toast.makeText(Blog_Detail.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("pid",blogid);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private String removeHtml(String html) {
        html = html.replaceAll("<(.*?)\\>"," ");
        html = html.replaceAll("<(.*?)\\\n"," ");
        html = html.replaceFirst("(.*?)\\>", " ");
        html = html.replaceAll("&nbsp;"," ");
        html = html.replaceAll("&amp;"," ");
        return html;
    }

}

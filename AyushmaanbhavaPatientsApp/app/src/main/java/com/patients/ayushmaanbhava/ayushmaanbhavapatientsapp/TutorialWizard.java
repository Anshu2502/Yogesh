package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.codepond.wizardroid.WizardFlow;
import org.codepond.wizardroid.WizardFragment;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



public class TutorialWizard extends WizardFragment implements View.OnClickListener {
   Button nextButton, previousButton;
    String name,email,number,password,id_pic_name,user_pic_name,u_id,u_id_type,gender_type,street,state,city,otp,notees,blood,
            land_mark,pincode,marriage,education,occupation,emergency_name,relation,emergency_number,adhaar_pic_64,user_pic_64,age;
    int num = 1;
    TextView txt;
    LayoutInflater li;
    EditText editTextConfirmOtp;
    SharedPreferences pref;
    private BroadcastReceiver mIntentReceiver;
    private RequestQueue requestQueue;




    //You must have an empty constructor according to Fragment documentation
    public TutorialWizard() {
    }
    /**
     * Binding the layout and setting buttons hooks
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View wizardLayout = inflater.inflate(R.layout.wizard, container, false);

        txt = (TextView) wizardLayout.findViewById(R.id.textView);
        nextButton = (Button) wizardLayout.findViewById(R.id.wizard_next_button);
        nextButton.setOnClickListener(this);
        previousButton = (Button) wizardLayout.findViewById(R.id.wizard_previous_button);
        previousButton.setOnClickListener(this);
        pref = getActivity().getPreferences(Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(getActivity());
      //  Toast.makeText(getActivity(), String.valueOf(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*4), Toast.LENGTH_SHORT).show();

        wizardLayout.setFocusableInTouchMode(true);
        wizardLayout.requestFocus();

        try{

        wizardLayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                        preferences .edit().clear().apply();
                        Intent p = new Intent(getActivity(),MainActivity.class);
                        startActivity(p);
                        getActivity().finish();
                        return true;
                    }
                }
                return false;
            }
        });
        }catch (IllegalStateException e){
            Toast.makeText(getActivity(), "Got Error", Toast.LENGTH_SHORT).show();
        }
        return wizardLayout;
    }

    //You must override this method and create a wizard flow by
    //using WizardFlow.Builder as shown in this example
    @Override
    public WizardFlow onSetup() {
        return new WizardFlow.Builder()
                .addStep(TutorialStep1.class)          //Add your steps in the order you want them
                .addStep(TutorialStep2.class)
                .addStep(TutorialStep3.class)
                .addStep(TutorialStep4.class)//to appear and eventually call create()
                .create();                              //to create the wizard flow.
    }

    /**
     * Triggered when the wizard is completed.
     * Overriding this method is optional.
     */
    @Override
    public void onWizardComplete() {
        number = pref.getString("number", "");  //important

      //  Toast.makeText(getActivity(), number, Toast.LENGTH_SHORT).show();

        password = pref.getString("password", "empty"); //important
        u_id = pref.getString("u_id_number", "");

        if(number.length()>=10){
         //  Toast.makeText(getActivity(), password, Toast.LENGTH_SHORT).show();
            if(!Objects.equals(password, "empty") ||password.length()>0){

                if(u_id.length()>=10){
                    register();
                }else {

                    Toast.makeText(getActivity(), "Not Valid Adhaar Number", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                    preferences .edit().clear().apply();
                }
            }else{
                Toast.makeText(getActivity(), "Not Valid Password", Toast.LENGTH_SHORT).show();
                SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                preferences .edit().clear().apply();

            }

        }else{
            Toast.makeText(getActivity(), "Not Valid number", Toast.LENGTH_SHORT).show();
            SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            preferences .edit().clear().apply();
          //  Toast.makeText(getActivity(), number, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.wizard_next_button:
                if(num<=4){

                    if(num==4){
                        txt.setText("Registration Step "+ String.valueOf(num) +" of 4 ");
                        wizard.goNext();
                    }else {
                    num++;
                    txt.setText("Registration Step "+ String.valueOf(num) +" of 4 ");
                    wizard.goNext();
                    }
                }

                break;
            case R.id.wizard_previous_button:
                num--;
                txt.setText("Registration Step "+ String.valueOf(num) +" of 4 ");
                wizard.goBack();
                break;
        }
        updateWizardControls();
    }

    /**
     * Updates the UI according to current step position
     */
    private void updateWizardControls() {

        previousButton.setEnabled(!wizard.isFirstStep());
        nextButton.setText(wizard.isLastStep()
                ? R.string.action_finish
                : R.string.action_next);
    }


    public void register(){

        //Step 1
        name = pref.getString("name", "");
        email = pref.getString("mail", "");
        blood = pref.getString("blood_type", "");
        age = pref.getString("age","");
        id_pic_name = pref.getString("id_name", "");
        user_pic_name = pref.getString("user_name", "");
        adhaar_pic_64 = pref.getString("iimage", "");
        user_pic_64 = pref.getString("image", "");
        u_id = pref.getString("u_id_number", "");
        u_id_type = pref.getString("u_id_type", "");
        gender_type = pref.getString("gender_type", "");
        notees = pref.getString("notee", "");
        otp = ""+((int)(Math.random()*9000)+1000);

        //Step 2
        street = pref.getString("street", "");
        state = pref.getString("state", "");
        city = pref.getString("city", "");
        land_mark = pref.getString("land_mark", "");
        pincode = pref.getString("pincode", "");

        //Step 3
        marriage = pref.getString("marriage", "");
        education = pref.getString("education", "");
        occupation = pref.getString("occupation", "");

        //Step 4
        emergency_name = pref.getString("emergency_name", "");
        relation = pref.getString("relation", "");
        emergency_number = pref.getString("emergency_phone", "");

        final ProgressDialog loading = ProgressDialog.show(getActivity(), "Registering", "Please wait", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/register.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if(response.trim().equalsIgnoreCase("success")){
                            loading.dismiss();


                            try {
                                confirmOtp();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            loading.dismiss();
                            Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        loading.dismiss();
                    }
                }){




            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("number", number);
                params.put("blood",blood);
                params.put("password", password);
                params.put("id_pic_name", id_pic_name);
                params.put("user_pic_name", user_pic_name);
                params.put("u_id", u_id);
                params.put("gender_type", gender_type);
                params.put("notes", notees);
                params.put("otp", otp);
                params.put("age",age);
                params.put("u_id_type", u_id_type);

                if(!Objects.equals(user_pic_64, "empty")){
                    params.put("u_pic", user_pic_64);
                }
                if(!Objects.equals(adhaar_pic_64, "empty")){
                    params.put("id_pic", adhaar_pic_64);
                }

                //step 2
                params.put("street", street);
                params.put("state", state);
                params.put("city", city);
                params.put("land_mark", land_mark);
                params.put("pincode", pincode);

                //step 3
                params.put("marriage", marriage);
                params.put("education", education);
                params.put("occupation", occupation);

                //step 4
                params.put("emergency_name", emergency_name);
                params.put("relation", relation);
                params.put("emergency_number", emergency_number);

                System.out.println(params);

                return params;
            }
        };

        RequestQueue requestQueuee = Volley.newRequestQueue(getActivity());
       stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueuee.add(stringRequest);
    }





    private void confirmOtp() throws JSONException {
        li = LayoutInflater.from(getActivity());
        View confirmDialog = li.inflate(R.layout.dialog_confirm, null);
        Button buttonConfirm = (AppCompatButton) confirmDialog.findViewById(R.id.buttonConfirm);
        editTextConfirmOtp = (EditText) confirmDialog.findViewById(R.id.editTextOtp);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(confirmDialog);

        final AlertDialog alertDialog = alert.create();

        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);

        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("get_msg");
                msg = msg.replace("\n", "");
                String body = msg.substring(msg.lastIndexOf(":")+1, msg.length());
//                String pNumber = msg.substring(0,msg.lastIndexOf(":"));
                editTextConfirmOtp.setText(body);
            }
        };

        getActivity().registerReceiver(mIntentReceiver, intentFilter);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                alertDialog.dismiss();


                final ProgressDialog loading = ProgressDialog.show(getActivity(), "Authenticating", "Please wait while we check the entered code", false, false);


                final String otp = editTextConfirmOtp.getText().toString().trim();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://ayushmaanbhavahealingcenter.com/admin/app/confirm.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                                if(response.equalsIgnoreCase("success")){


                                    loading.dismiss();
                                    SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                                    preferences .edit().clear().apply();
                                    //       Toast.makeText(Register.this,"Your Registration is Completed",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getActivity(), Login.class));
                                    getActivity().finish();
                                }else{


                                    try {

                                        confirmOtp();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                alertDialog.dismiss();

                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();

                        params.put("otp", otp);
                        params.put("contact", number);
                        return params;
                    }
                };

                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(stringRequest);
            }
        });
      }






   }

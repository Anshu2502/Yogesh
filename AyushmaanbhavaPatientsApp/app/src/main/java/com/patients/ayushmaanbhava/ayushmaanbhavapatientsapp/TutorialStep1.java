package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TutorialStep1 extends WizardStep {
    String[] SPINNERLIST = {"Select","O+", "O-", "A+", "A-","B+","B-","AB+","AB-"};
    @ContextVariable
    private String blood_type;
    @ContextVariable
    private String uid;
    @ContextVariable
    private String gender_type;
    @ContextVariable
    private String file_name,file_name2;
    @ContextVariable
    private String img_type;
    RadioGroup rg,gender;
    private Uri filePath,filePath2;
    private Bitmap bitmap,bitmap2;
    private static final int PERMISSION_REQUEST_CODE = 1;
    int result,locationPermission,locationPermissions;
    EditText full_name, pnumber,maill,password, cpassword, img_adhaar,img_user,notess,ind, nind,u_age;
    ImageButton adhaar_passport, user_image;
    private static final int SELECT_PICTURE = 100;


    public TutorialStep1() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_tutorial, container, false);



        adhaar_passport = (ImageButton)v.findViewById(R.id.do_email_picker);
        user_image = (ImageButton)v.findViewById(R.id.do_img_picker);
        full_name = (EditText)v.findViewById(R.id.fName);
        pnumber = (EditText)v.findViewById(R.id.fNumber);
        maill = (EditText)v.findViewById(R.id.fMail);
        password = (EditText)v.findViewById(R.id.fPassword);
        cpassword = (EditText)v.findViewById(R.id.fcPassword);
        img_adhaar = (EditText)v.findViewById(R.id.img_hint);
        img_user = (EditText)v.findViewById(R.id.user_hint);
        ind = (EditText)v.findViewById(R.id.fadhaar);
        nind = (EditText)v.findViewById(R.id.fPassport);
        notess = (EditText)v.findViewById(R.id.note);
        u_age=(EditText)v.findViewById(R.id.age);
        MaterialSpinner materialDesignSpinner = (MaterialSpinner)
                v.findViewById(R.id.android_material_design_spinner);

        materialDesignSpinner.setItems(SPINNERLIST);



        ind.addTextChangedListener(new TextWatcher() {
            int len=0;
            String str;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                str = ind.getText().toString();
                len = str.length();

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              //  str = ind.getText().toString();
                String str = ind.getText().toString();
                if(str.length()==4&& len <str.length()||str.length()==9&& len <str.length()){//len check for backspace


                    if(ind.getText().length()<=14){
                    //    Toast.makeText(getActivity(), String.valueOf(ind.getText().length()), Toast.LENGTH_SHORT).show();
                        String getnow = ind.getText().toString();
                        ind.setText(getnow+"-");
                        ind.setSelection(ind.getText().length());
                    }

                }




            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        materialDesignSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {


               blood_type = item;
            }
        });
        materialDesignSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });

         rg = (RadioGroup) v.findViewById(R.id.radio_nationality);
         gender = (RadioGroup) v.findViewById(R.id.radio_gender);
        ind.setVisibility(View.VISIBLE);
        nind.setVisibility(View.GONE);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch(i){
                        case R.id.radio_indian:
                             uid = "Indian";
                            ind.setVisibility(View.VISIBLE);
                            nind.setVisibility(View.GONE);
                            break;
                        case R.id.radio_notindian:
                               uid = "NRI";
                            ind.setVisibility(View.GONE);
                            nind.setVisibility(View.VISIBLE);
                           break;

                    }
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.radio_male:
                        gender_type = "Male";

                        break;
                    case R.id.radio_female:
                        gender_type = "Female";

                        break;

                }
            }
        });


        adhaar_passport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_type ="idd";
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

        user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_type ="userimd";
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


        return v;
    }
    private boolean checkPermission() {
        result = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE );
        locationPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS);
        locationPermissions = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);

        return result == PackageManager.PERMISSION_GRANTED && locationPermission == PackageManager.PERMISSION_GRANTED && locationPermissions == PackageManager.PERMISSION_GRANTED;
    }


    private boolean requestPermission() {

        List<String> listPermissionsNeeded = new ArrayList<>();

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
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //  Toast.makeText(this, "Accepted", Toast.LENGTH_SHORT).show();


                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                filePath = data.getData();
                if (null != filePath) {
                    try {
                        if(img_type.equals("idd")){
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                            if(bitmap!=null){
                                bitmap =  Bitmap.createScaledBitmap(bitmap, 960, 960, true);
                            }

                        }else {
                            bitmap2 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                            if(bitmap2!=null){

                                bitmap2 =  Bitmap.createScaledBitmap(bitmap2, 960, 960, true);
                            }


                        }


                        // img.setImageBitmap(bitmap);

                        if (filePath.getScheme().equals("content")) {
                            try (Cursor cursor = getActivity().getContentResolver().query(filePath, null, null, null, null)) {
                                if (cursor != null && cursor.moveToFirst()) {
                                    file_name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                    //     Toast.makeText(this, file_name, Toast.LENGTH_SHORT).show();
                                    if(img_type.equals("idd")){
                                        img_adhaar.setText(file_name);
                                    }else {
                                        img_user.setText(file_name);
                                    }

                                }
                            }
                        }else {

                            String path= data.getData().getPath();
                            file_name=path.substring(path.lastIndexOf("/")+1);
                            if(img_type.equals("idd")){
                                img_adhaar.setText(file_name);
                            }else {
                                img_user.setText(file_name);
                            }
                            //    Toast.makeText(this, file_name, Toast.LENGTH_SHORT).show();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
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

    public String getStringImage2(Bitmap bmp){

        if(bmp!=null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 40, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
        }

        return null;
    }



    @Override
    public void onExit(int exitCode) {

        switch (exitCode) {

            case WizardStep.EXIT_NEXT:

                if(pnumber.getText().length()>=10){

                    if(Objects.equals(password.getText().toString(), cpassword.getText().toString()) && cpassword.getText().length()>0){

                        if(uid!=null) {
                            Toast.makeText(getActivity(), blood_type, Toast.LENGTH_SHORT).show();
                            SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor edt = pref.edit();
                            edt.putString("name", full_name.getText().toString());
                            edt.putString("number", pnumber.getText().toString());
                            edt.putString("mail", maill.getText().toString());
                            edt.putString("blood_type", blood_type);
                            edt.putString("password", cpassword.getText().toString());
                            edt.putString("age", u_age.getText().toString());
                            final String id_image_64 = getStringImage(bitmap);
                            final String user_image_64 = getStringImage2(bitmap2);

                            if(id_image_64 !=null){
                                edt.putString("iimage", id_image_64);
                                edt.putString("id_name", img_adhaar.getText().toString());
                            }

                            if(user_image_64 !=null){
                                edt.putString("image", user_image_64);
                                edt.putString("user_name", img_user.getText().toString());
                            }

                            edt.putString("gender_type", gender_type);
                            if (uid.equals("Indian")) {
                                edt.putString("u_id_type", "Indian");
                                edt.putString("u_id_number", ind.getText().toString());
                            } else {
                                edt.putString("u_id_type", "NRI");
                                edt.putString("u_id_number", nind.getText().toString());
                            }
                            edt.putString("notee", notess.getText().toString());
                            edt.apply();
                        }else {
                            Toast.makeText(getActivity(), "Please Select Nationality", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getActivity(), "Password & Confirm Password must be same", Toast.LENGTH_LONG).show();
                    }



                }else {
                    Toast.makeText(getActivity(), "Please enter valid number", Toast.LENGTH_LONG).show();
                }
                break;
            case WizardStep.EXIT_PREVIOUS:
                //Do nothing...
                break;
        }

    }

    private void bindDataFields() {


        Toast.makeText(getActivity(), "ABC 2", Toast.LENGTH_SHORT).show();
        //Do some work
        //...
        //The values of these fields will be automatically stored in the wizard context
        //and will be populated in the next steps only if the same field names are used.
     //  firstname = ind.getText().toString();
//        lastname = lastnameEt.getText().toString();
    }



}

package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.codepond.wizardroid.WizardStep;



public class TutorialStep2 extends WizardStep {
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
    String state;
    EditText street, cityy,land_mark,pin_code;
        //You must have an empty constructor for every step
    public TutorialStep2() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_tutorial2, container, false);
         MaterialSpinner materialDesignSpinner = (MaterialSpinner) v.findViewById(R.id.android_material_design_spinner);
        street = (EditText)v.findViewById(R.id.fSbuilding);
        cityy = (EditText)v.findViewById(R.id.City);
        land_mark = (EditText) v.findViewById(R.id.Land);
        pin_code = (EditText)v.findViewById(R.id.fPincode);
        materialDesignSpinner.setItems(SPINNERLIST);
        materialDesignSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                state = item;
            }
        });
        materialDesignSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });

/*
        v.setFocusableInTouchMode(true);
        v.requestFocus();

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                       // Toast.makeText(getActivity(), "Back Pressed", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return false;
            }
        });
*/




        return v;
    }

   @Override
    public void onExit(int exitCode) {

        switch (exitCode) {

            case WizardStep.EXIT_NEXT:
                SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = pref.edit();
                edt.putString("street", street.getText().toString());
                edt.putString("state", state);
                edt.putString("city", cityy.getText().toString());
                edt.putString("land_mark", land_mark.getText().toString());
                edt.putString("pincode", pin_code.getText().toString());
                edt.apply();




                break;
            case WizardStep.EXIT_PREVIOUS:
                break;

        }
    }








        }
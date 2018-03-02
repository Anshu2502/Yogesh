package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.codepond.wizardroid.WizardStep;
import org.codepond.wizardroid.persistence.ContextVariable;


public class TutorialStep4 extends WizardStep {
    String[] SPINNERLIST = {"Mother", "Father", "Husband","Wife","Daughter","Son","Brother","Sister","Grand Father","Grand Mother","Friend","Other"};
    String relation;
    EditText e_name, e_number;
    TextView termsCondition;

    //Wire the layout to the step
    @ContextVariable
    private String firstname;
    public TutorialStep4() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_tutorial4, container, false);
        //Toast.makeText(getActivity(), firstname, Toast.LENGTH_SHORT).show();
        MaterialSpinner materialDesignSpinner = (MaterialSpinner)
                v.findViewById(R.id.android_material_design_spinner);
        e_name = (EditText)v.findViewById(R.id.fNamee);
        e_number = (EditText)v.findViewById(R.id.fContact);
        termsCondition = (TextView)v.findViewById(R.id.tCondition);
        materialDesignSpinner.setItems(SPINNERLIST);
        materialDesignSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                relation = item;
            }
        });
        materialDesignSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });

        termsCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://ayushmaanbhavahealingcenter.com/terms-conditions-privacy/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


            }
        });

        return v;
    }

    @Override
    public void onExit(int exitCode) {

        switch (exitCode) {

            case WizardStep.EXIT_NEXT:
                SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor edt = pref.edit();
                edt.putString("emergency_name", e_name.getText().toString());
                edt.putString("relation", relation);
                edt.putString("emergency_phone",e_number.getText().toString());
                edt.apply();


                break;
            case WizardStep.EXIT_PREVIOUS:
                break;
        }
    }
}

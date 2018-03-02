package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaredrummler.materialspinner.MaterialSpinner;

import org.codepond.wizardroid.WizardStep;

public class TutorialStep3 extends WizardStep {
    String marriage, education, occupation;
    String[] SPINNERLIST = {"Single", "Married", "Widowed","Divorced"};
    String[] SPINNERLIST2 = {"Primary", "Secondary", "Diploma","Bachelor","Masters","Doctorate","None","Others"};
    String[] SPINNERLIST3 = {"Unemployed", "Retired", "Professinal","Self Employed","Home Maker","Salaried","Student","Others"};
    //Wire the layout to the step
    public TutorialStep3() {
    }

    //Set your layout here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.step_tutorial3, container, false);

        MaterialSpinner materialDesignSpinner = (MaterialSpinner)
                v.findViewById(R.id.android_material_design_spinner);
        MaterialSpinner materialDesignSpinner2 = (MaterialSpinner)
                v.findViewById(R.id.android_material_design_spinner2);
        MaterialSpinner materialDesignSpinner3 = (MaterialSpinner)
                v.findViewById(R.id.android_material_design_spinner3);

        materialDesignSpinner.setItems(SPINNERLIST);
        materialDesignSpinner2.setItems(SPINNERLIST2);
        materialDesignSpinner3.setItems(SPINNERLIST3);

        materialDesignSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                marriage = item;
            }
        });
        materialDesignSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });

        materialDesignSpinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                education = item;
            }
        });
        materialDesignSpinner2.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

            }
        });


        materialDesignSpinner3.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                occupation = item;
            }
        });
        materialDesignSpinner3.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {

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

                edt.putString("marriage", marriage);
                edt.putString("education", education);
                edt.putString("occupation", occupation);


                edt.apply();



                break;

            case WizardStep.EXIT_PREVIOUS:
                break;

        }
    }











}

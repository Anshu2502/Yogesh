package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Fees extends AppCompatActivity {
Button medicine, doctorfee;
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.fees);

        medicine = (Button)findViewById(R.id.med);
        doctorfee = (Button)findViewById(R.id.doc);

doctorfee.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent  o = new Intent(Fees.this,Doc_Fee.class);
        startActivity(o);
    }
});

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  o = new Intent(Fees.this,Invoice.class);
                startActivity(o);
            }
        });



        }


    }

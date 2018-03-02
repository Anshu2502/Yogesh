package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button login = (Button)findViewById(R.id.buttonLogin);
        Button register = (Button)findViewById(R.id.buttonRegister);
        Button change_num = (Button)findViewById(R.id.buttonnumberchange);

        change_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(MainActivity.this,Change_Number1.class);
                startActivity(ii);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(MainActivity.this,Login.class);
                startActivity(ii);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(MainActivity.this,Register.class);
                startActivity(ii);
            }
        });





        // its second
    }

}


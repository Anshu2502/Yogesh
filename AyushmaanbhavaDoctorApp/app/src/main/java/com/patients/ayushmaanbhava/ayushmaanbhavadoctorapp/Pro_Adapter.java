package com.patients.ayushmaanbhava.ayushmaanbhavadoctorapp;

/**
 * Created by Pitech09 on 9/7/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class Pro_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Pro_Cons> data= Collections.emptyList();
    Pro_Cons current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public Pro_Adapter(Context context, List<Pro_Cons> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_pro, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Pro_Cons current=data.get(position);
        myHolder.type.setText(current.date);


        ((MyHolder) holder).details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pro_Cons current=data.get(position);
                Intent intent = new Intent(context,Appointment_Detail.class);
                intent.putExtra("appoint_id",current.id);
                context.startActivity(intent);
                //((Activity)context).finish();

         //     Toast.makeText(context, String.valueOf(current.id), Toast.LENGTH_LONG).show();
            }
        });

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        Button details;
        TextView type;



        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            type = (TextView) itemView.findViewById(R.id.datess);
            details = (Button) itemView.findViewById(R.id.button11);
        }

    }

}
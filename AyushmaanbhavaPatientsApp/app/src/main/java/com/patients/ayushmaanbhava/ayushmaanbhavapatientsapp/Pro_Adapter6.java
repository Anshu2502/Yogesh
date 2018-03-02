package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class Pro_Adapter6 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Pro_Cons> data= Collections.emptyList();
    Pro_Cons current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public Pro_Adapter6(Context context, List<Pro_Cons> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_pro4, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Pro_Cons current=data.get(position);
        myHolder.m_name.setText(current.med_name);
        myHolder.m_price.setText(current.med_price);
        myHolder.m_quantity.setText(current.med_quantity);

        myHolder.m_final_price.setText(current.med_final_price);



      /*  ((MyHolder) holder).details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pro_Cons current=data.get(position);
                Intent intent = new Intent(context,Invoice_Detail.class);
                intent.putExtra("appoint_id",current.appoint_id);
                context.startActivity(intent);
                // ((Activity)context).finish();

                Toast.makeText(context, String.valueOf(current.appoint_id), Toast.LENGTH_LONG).show();
            }
        });*/

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        Button details;
        TextView m_name,m_price,m_quantity,m_final_price;




        public MyHolder(View itemView) {
            super(itemView);
            //  filename = (TextView)itemView.findViewById(R.id.file_name);
            m_name = (TextView) itemView.findViewById(R.id.mname);
            m_price = (TextView) itemView.findViewById(R.id.mprice);
            m_quantity = (TextView) itemView.findViewById(R.id.mquantity);

            m_final_price = (TextView) itemView.findViewById(R.id.final_price);


        }

    }

}
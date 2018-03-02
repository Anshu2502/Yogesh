package com.patients.ayushmaanbhava.ayushmaanbhavapatientsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class Pro_Adapter4 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Pro_Cons> data= Collections.emptyList();
    Pro_Cons current;
    int currentPos=0;

    // create constructor to innitilize context and data sent from MainActivity
    public Pro_Adapter4(Context context, List<Pro_Cons> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_pro3, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        Pro_Cons current=data.get(position);
        myHolder.type.setText(current.bolg_name);
        myHolder.date.setText(current.blog_date);
        Picasso.with(context)
                .load("http://ayushmaanbhavahealingcenter.com/admin/adminassets/images/" + current.imagev)
                .placeholder(R.mipmap.patient)        //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(myHolder.img);

        ((MyHolder) holder).details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pro_Cons current=data.get(position);
                Intent intent = new Intent(context,Blog_Detail.class);
                intent.putExtra("blog_id",current.blog_id);
                context.startActivity(intent);
                // ((Activity)context).finish();

                 Toast.makeText(context, String.valueOf(current.blog_id), Toast.LENGTH_LONG).show();
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
        TextView type,date,filename;
        ImageView img;




        public MyHolder(View itemView) {
            super(itemView);
            //  filename = (TextView)itemView.findViewById(R.id.file_name);
            type = (TextView) itemView.findViewById(R.id.textViewName);
            date = (TextView) itemView.findViewById(R.id.textViewTeam);
            details = (Button) itemView.findViewById(R.id.buttonDelete);
            img = (ImageView) itemView.findViewById(R.id.imageView);

        }

    }

}
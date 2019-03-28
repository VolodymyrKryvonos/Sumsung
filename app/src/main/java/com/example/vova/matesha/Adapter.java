package com.example.vova.matesha;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>  {

    int id=0;
    ArrayList<String> mStrings;
    public Adapter() {
//        mStrings = Chapter.s;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.chapterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
//                Intent intent = new Intent(context,)
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

public class ViewHolder extends RecyclerView.ViewHolder {
    Button chapterBtn;
    public ViewHolder(View itemView) {
        super(itemView);
        chapterBtn = itemView.findViewById(R.id.list_item);
    }
}
}

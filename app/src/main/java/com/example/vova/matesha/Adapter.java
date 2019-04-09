package com.example.vova.matesha;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    ArrayList<String> mStrings;
    onBtnClickListener listener;
    public Adapter(ArrayList<String> s, onBtnClickListener listener) {
        mStrings = s;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String chapter = mStrings.get(position);
        holder.chapterBtn.setText(chapter);
        holder.chapterBtn.setAllCaps(false);
        final int finalPosition = position;
        holder.chapterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onBtnClickListener(finalPosition, holder.chapterBtn.getText().toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStrings == null ? 0 : mStrings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button chapterBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            chapterBtn = itemView.findViewById(R.id.list_item);
        }
    }

    public interface onBtnClickListener{
        void onBtnClickListener(int position,String name);
    }

}

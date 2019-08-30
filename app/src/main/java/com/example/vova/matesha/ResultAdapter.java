package com.example.vova.matesha;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.Holder> {


    Context context;
    ArrayList<Result> results;
    DBHelper helper;
    SQLiteDatabase database;


    public ResultAdapter(ArrayList<Result> results, Context context) {
        this.results = results;
        this.context = context;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("onCreateViewHolder", "onCreateViewHolder");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        holder.name.setText(results.get(position).name);
        holder.result.setText(holder.result.getText() + (results.get(position).result + ""));
        holder.score.setText(holder.score.getText() + (results.get(position).score + ""));
        holder.date.setText(results.get(position).date);

        if (results.get(position).score == 0) {
            holder.mood.setImageResource(R.drawable.strawberry);
            holder.textMood.setText(context.getText(R.string.bad_mood));
        } else if (results.get(position).score <= 170 && results.get(position).score > 130) {
            holder.mood.setImageResource(R.drawable.zno);
        } else if (results.get(position).score > 170 && results.get(position).score <= 185) {
            holder.mood.setImageResource(R.drawable.zno);
        } else if (results.get(position).score > 185 && results.get(position).score != 200) {
            holder.mood.setImageResource(R.drawable.zno);
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public static class Result {
        String date, name;
        int result, score;

        public Result(String date, String name, int result, int score) {
            this.date = date;
            this.name = name;
            this.result = result;
            this.score = score;
        }
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView result, score, date, name, textMood;
        ImageView mood;

        public Holder(View itemView) {

            super(itemView);
            textMood = itemView.findViewById(R.id.text_mood);
            result = itemView.findViewById(R.id.correct);
            score = itemView.findViewById(R.id.score);
            date = itemView.findViewById(R.id.date);
            name = itemView.findViewById(R.id.name);
            mood = itemView.findViewById(R.id.mood);

        }


    }

}

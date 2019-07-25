package com.example.vova.matesha;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ResultActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    ArrayList<ResultAdapter.Result> results = new ArrayList<>();
    DBHelper helper;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        fillResults();
        recyclerView = findViewById(R.id.results);
        adapter = new ResultAdapter(results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private int fillResults() {
        helper = new DBHelper(ResultActivity.this);
        database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM results", null);
        Log.e("Count", cursor.getCount() + "");
        if (cursor.moveToFirst()) {
            Log.e("fillResults", "fillResults");


            do {

                results.add(new ResultAdapter.Result(cursor.getString(3), cursor.getString(0), cursor.getInt(1), cursor.getInt(2)));

            } while (cursor.moveToNext());

            Collections.sort(results, new Comparator<ResultAdapter.Result>() {
                @Override
                public int compare(ResultAdapter.Result result, ResultAdapter.Result t1) {
                    return result.date.compareTo(t1.date);
                }
            });

            Collections.reverse(results);
        }
        return results.size();
    }
}

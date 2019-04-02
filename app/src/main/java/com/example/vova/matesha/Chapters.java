package com.example.vova.matesha;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Chapters extends Fragment {

    static Chapters newInstance(int page) {
        Chapters pageFragment = new Chapters();
        return pageFragment;
    }

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DBHelper helper;
    ArrayList<String> chapters = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.algebra_chapters_fragment,container,false);
        try {
            fillChapters();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView = view.findViewById(R.id.algebra_list);
        adapter = new Adapter(chapters);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void fillChapters() throws IOException {
        String sub = "1";
        Intent intent = getActivity().getIntent();
        int from = intent.getExtras().getInt(ChoiseAction.SUB_KEY);
        if (from == 0){
            sub = "1";
        }
        else sub = "2";

        helper = new DBHelper(getContext());
        try {
            helper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            helper.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }

        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor query = db.rawQuery("SELECT chapter FROM chapters WHERE subject=1 ;", new String[]{sub});
        if (query.moveToFirst()){
            do {
                chapters.add(query.getString(1));
            }
            while (query.moveToNext());

        }
        query.close();
        db.close();

    }
}

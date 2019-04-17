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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Chapters extends Fragment implements Adapter.onBtnClickListener {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    DBHelper helper;
    int id;
    int from;
    ArrayList<Chapter> chapters = new ArrayList<>();
    SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.algebra_chapters_fragment, container, false);
        try {
            fillChapters();
            Log.e("OnCreateView","OnCreateView");
        } catch (IOException e) {
            e.printStackTrace();
        }

        recyclerView = view.findViewById(R.id.algebra_list);
        adapter = new Adapter(chapters, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("ID");
    }

    private void fillChapters() throws IOException {
        Intent intent = getActivity().getIntent();
        helper = new DBHelper(getContext());
        from = intent.getExtras().getInt(ChoiseAction.INTENT_KEY);
        db = helper.getReadableDatabase();
        Cursor query;
        switch (from){
            case 1:{
                query= db.rawQuery("SELECT chapter, _id FROM chapters WHERE subject=? ;", new String[]{id+""});
                break;
            }
            case 3:{
                query= db.rawQuery("SELECT chapter, _id FROM tasks WHERE subject=? ;", new String[]{id+""});
                break;
            }
            default:query = null;
        }

        if (query.moveToFirst()&&query!=null) {
            do {
                chapters.add(new Chapter(query.getString(0), query.getInt(1)));
            }
            while (query.moveToNext());

        }
        query.close();

    }

    @Override
    public void onBtnClickListener(int id) {
        Intent intent;
        if(from == 1){
        intent = new Intent(getActivity(), HolderActivity.class);
        intent.putExtra("ID", id);}
        else {        intent = new Intent(getActivity(), TaskActivity.class);
            intent.putExtra("SUBJECT", this.id);
            Cursor cursor = db.rawQuery("SELECT chapter FROM tasks WHERE _id=? ;", new String[]{id+""});
            cursor.moveToFirst();
            intent.putExtra("CHUPTER", cursor.getString(0));}
        startActivity(intent);
    }


    static Chapters newInstance(int id) {
        Chapters pageFragment = new Chapters();
        Bundle arguments = new Bundle();
        arguments.putInt("ID", id);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }
}

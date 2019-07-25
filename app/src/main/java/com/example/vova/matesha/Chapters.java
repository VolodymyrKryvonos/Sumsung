package com.example.vova.matesha;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Chapters extends Fragment implements ChapterAdapter.onBtnClickListener, Serializable {

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
        fillChapters();
        recyclerView = view.findViewById(R.id.algebra_list);
        adapter = new ChapterAdapter(chapters, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("ID");
        Intent intent = getActivity().getIntent();
        helper = new DBHelper(getContext());
        from = intent.getExtras().getInt(ChoiseAction.INTENT_KEY);
        final SharedPreferences preferences  = getActivity().getSharedPreferences(ZnoHolder.ANSWERS,MODE_PRIVATE);
        db = helper.getReadableDatabase();
        if(from==2&&preferences.getBoolean("unfinished test",false)){
            new AlertDialog.Builder(getContext())
                    .setTitle("Незавершений тест")
                    .setMessage("У вас є незавершений тест, ви можете продовжити його, або видалити.")
                    .setPositiveButton("Продовжити", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent intent = new Intent(getActivity(), ZnoHolder.class);
                            intent.putExtra("ID",preferences.getInt("ID_ZNO",0));
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Видалити", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    dialog.dismiss();
                }
            }).show();

        }
    }

    private void fillChapters() {

        Cursor query;
        switch (from) {
            case 1: {
                query = db.rawQuery("SELECT chapter, _id FROM chapters WHERE subject=?", new String[]{id + ""});
                break;
            }
            case 2: {
                query = db.rawQuery("SELECT chapter, _id FROM ZNO", null);
                break;
            }
            default:
                query = null;
        }

        if (query.moveToFirst() && query != null) {
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
        if (from == 1) {
            intent = new Intent(getActivity(), HolderActivity.class);
            intent.putExtra("ID", id);
            startActivity(intent);
        }
        else {
            SharedPreferences preferences = getActivity().getSharedPreferences(ZnoHolder.ANSWERS, MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            intent = new Intent(getActivity(), ZnoHolder.class);
            intent.putExtra("ID",id);
            startActivityForResult(intent,0);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getExtras().getBoolean("finish")){
            getActivity().finish();
        }
    }

    static Chapters newInstance(int id) {
        Chapters pageFragment = new Chapters();
        Bundle arguments = new Bundle();
        arguments.putInt("ID", id);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }
}
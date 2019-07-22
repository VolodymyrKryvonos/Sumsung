package com.example.vova.matesha;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class ZnoHolder extends AppCompatActivity {
boolean finished = false;
    ViewPager tasks;
    FragmentManager fragmentManager;
    DBHelper helper;
    Cursor query;
    Intent intent;
    TabLayout tabLayout;
    FloatingActionButton fab;
    int id;
    SharedPreferences answers;
    final static String ANSWERS = "ZNO_answers";
    SQLiteDatabase database;

    @Override
    protected void onDestroy() {
        Log.e("onDestroy","onDestroy");
        if (!answers.getBoolean("unfinished test",false)){
            Log.e("onDestroy","onDestroy");
            SharedPreferences.Editor editor = answers.edit();
            editor.clear().commit();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(!finished) {
            new AlertDialog.Builder(ZnoHolder.this)
                    .setTitle("Вийти з тесту")
                    .setMessage("Зберегти відповіді?")
                    .setPositiveButton("Зберегти", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            SharedPreferences.Editor editor = answers.edit();
                            editor.putBoolean("unfinished test", true);
                            editor.putInt("ID_ZNO", id);
                            editor.apply();
                            setResult(RESULT_OK, new Intent().putExtra("finish", true));
                            finish();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Вийти", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    SharedPreferences.Editor editor = answers.edit();
                    editor.clear();
                    editor.apply();
                    setResult(RESULT_OK, new Intent().putExtra("finish", false));
                    finish();
                    dialog.dismiss();
                }
            }).show();
        }
        else {setResult(RESULT_OK, new Intent().putExtra("finish", false));
            SharedPreferences.Editor editor = answers.edit();
            editor.clear();
            editor.apply();
            super.onBackPressed();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zno_holder);
        intent = getIntent();
        fab = findViewById(R.id.fab);
        tasks = findViewById(R.id.task);
        fragmentManager = getSupportFragmentManager();
        helper = new DBHelper(this);
        database = helper.getReadableDatabase();
        id = intent.getExtras().getInt("ID");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ZnoHolder.this);
                builder.setTitle("Завершити?")
                        .setMessage("Завершити та підрахувати бали.")
                        .setCancelable(false)
                        .setNegativeButton("Завершити",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        AlertDialog.Builder builder2 = new AlertDialog.Builder(ZnoHolder.this);
                                        builder2.setTitle("Тест завершено")
                                                .setMessage("Ваш бал складає " + checkAnswers())
                                                .setCancelable(false)
                                                .setPositiveButton("Подивитись",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                tasks.setCurrentItem(0);
                                                                SharedPreferences.Editor editor = answers.edit();
                                                                editor.putBoolean("finished",true);
                                                                editor.commit();
                                                                finished = true;
                                                                fab.setVisibility(View.GONE);
                                                                dialog.cancel();
                                                            }
                                                        })
                                                .setNegativeButton("Вийти", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int i) {
                                                        SharedPreferences.Editor editor = answers.edit();
                                                        editor.clear();
                                                        editor.apply();
                                                        editor.commit();
                                                        setResult(RESULT_OK, new Intent().putExtra("finish", false));
                                                        finish();
                                                        dialog.cancel();
                                                    }
                                                });
                                        AlertDialog alert = builder2.create();
                                        alert.show();
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton("Відмінити", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        query = database.rawQuery("SELECT number FROM ZNO WHERE _id = ?", new String[]{id + ""});

        tasks.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return Question.newInstance(i + 1);
            }

            @Override
            public int getCount() {
                query.moveToFirst();
                return query.getInt(0);
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "Запитання: " + (position + 1);
            }

        });
        answers = getSharedPreferences(ANSWERS, Context.MODE_PRIVATE);
        tabLayout = findViewById(R.id.namber);
        tabLayout.setupWithViewPager(tasks);
        tabLayout.setTabTextColors(Color.BLACK, Color.WHITE);
    }

    private float checkAnswers() {
        int scores = 0;
        Cursor cursor = database.rawQuery("SELECT number, answers FROM first_level WHERE _id=?", new String[]{id + ""});
        cursor.moveToFirst();
        do {
            if (cursor.getString(1).equals(answers.getString(cursor.getInt(0) + "", ""))) {
                scores++;
            }
        } while (cursor.moveToNext());

        cursor = database.rawQuery("SELECT number, right_answer_1, right_answer_2, right_answer_3, right_answer_4 FROM second_level WHERE _id=?", new String[]{id + ""});
        cursor.moveToFirst();
        do {
            if (cursor.getString(1).equals(answers.getString(cursor.getInt(0) + "_1", ""))) {
                scores++;
            }
            if (cursor.getString(2).equals(answers.getString(cursor.getInt(0) + "_2", ""))) {
                scores++;
            }
            if (cursor.getString(3).equals(answers.getString(cursor.getInt(0) + "_3", ""))) {
                scores++;
            }
            if (cursor.getString(4).equals(answers.getString(cursor.getInt(0) + "_4", ""))) {
                scores++;
            }
        } while (cursor.moveToNext());

        cursor = database.rawQuery("SELECT number,answer1,answer2,divided FROM third_level WHERE _id=?", new String[]{id + ""});
        cursor.moveToFirst();

        do {


            Log.e("1 ANSWER: ",Float.parseFloat(answers.getString(cursor.getInt(0) + "_1", "0.99"))+"");
            Log.e("right 1 ANSWER: ",cursor.getFloat(1)+"");

            Log.e("COMPARE " ,(Float.parseFloat(answers.getString(cursor.getInt(0) + "_1", "0.99"))== cursor.getFloat(1))+"");

            if (cursor.getInt(3) == 1) {
                if (Float.parseFloat(answers.getString(cursor.getInt(0) + "_1", "0.99"))== cursor.getFloat(1)) {
                    scores++;
                }
                if (Float.parseFloat(answers.getString(cursor.getInt(0) + "_2", "0.99"))== cursor.getFloat(2)) {
                    scores++;
                }
            }
            else if (Float.parseFloat(answers.getString(cursor.getInt(0) + "_1", "0.99"))== cursor.getFloat(1)) {
                scores += 2;
            }
        } while (cursor.moveToNext());
        cursor.moveToLast();

        for (int i = cursor.getInt(0) + 1; i <= query.getInt(0); i++)
            scores += answers.getInt(i + "", 0);

        return scores;
    }


}

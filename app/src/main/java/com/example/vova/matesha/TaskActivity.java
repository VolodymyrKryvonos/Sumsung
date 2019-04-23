package com.example.vova.matesha;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntRange;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.github.kexanie.library.MathView;

public class TaskActivity extends AppCompatActivity {
    DBHelper helper;
    Button nextBtn, checkBtn, looser;
    EditText answer;
    MathView task;
    Intent intent;
    Cursor cursor;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        intent = getIntent();
        nextBtn = findViewById(R.id.next);
        checkBtn = findViewById(R.id.check);
        task = findViewById(R.id.task);
        answer = findViewById(R.id.answer);
        looser = findViewById(R.id.loser);

        helper = new DBHelper(this);
        database = helper.getReadableDatabase();
        updateTask();


        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (answer.getText() != null) {
                    if (answer.getText().toString().equals(cursor.getString(1))) {
                        Toast.makeText(TaskActivity.this, "Correct", Toast.LENGTH_LONG).show();
                        ContentValues cv = new ContentValues();
                        cv.put("isDone", 1);
                        database.update("tasks", cv, "_id=?", new String[]{cursor.getInt(2) + ""});
                        cursor.moveToNext();
                        updateTask();


                    } else Toast.makeText(TaskActivity.this, "Incorrect", Toast.LENGTH_LONG).show();
                }
                answer.setText("");
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToNext();
                updateTask();
            }
        });


    }

    private void updateTask() {

        if (cursor == null || cursor.isBeforeFirst() || cursor.isAfterLast()) {
            cursor = database.rawQuery("SELECT task, answer, _id, isDone FROM tasks WHERE chapter=? AND isDone=0",
                    new String[]{intent.getExtras().getString("CHUPTER")});
            cursor.moveToFirst();
        }
        if (cursor.getCount() == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
            builder.setTitle("Congratulations!")
                    .setMessage("Congratulations!")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("isDone", 0);
                                    database.update("tasks", contentValues, null, null);
                                    dialog.cancel();
                                    finish();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        } else if (cursor.getString(0) != null) {
            Log.e("Count", cursor.getCount() + "");
            task.setText("<font size=5><p align=\"center\">" + cursor.getString(0) + "</p></font>");
        }

        answer.setText("");
    }
}

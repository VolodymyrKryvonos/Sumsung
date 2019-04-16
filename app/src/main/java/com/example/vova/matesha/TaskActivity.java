package com.example.vova.matesha;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.IntRange;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.github.kexanie.library.MathView;

public class TaskActivity extends AppCompatActivity {

    Button nextBtn, checkBtn;
    EditText answer;
    MathView task;
    Intent intent;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        intent = getIntent();
        nextBtn = findViewById(R.id.next);
        checkBtn = findViewById(R.id.check);
        task = findViewById(R.id.task);
        answer = findViewById(R.id.answer);

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        cursor = database.rawQuery("SELECT task, answer, _id FROM tasks WHERE subject=?", new String[]{intent.getExtras().getInt("SUBJECT") + ""});

        cursor.moveToFirst();

        while ((cursor.getInt(2) != intent.getExtras().getInt("ID") && (cursor.moveToNext()))) {
        }

        updateTask();


        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (answer.getText() != null) {
                    if (answer.getText().toString().equals(cursor.getString(1))) {
                        Toast.makeText(TaskActivity.this, "Correct", Toast.LENGTH_LONG).show();
                    } else Toast.makeText(TaskActivity.this, "Incorrect", Toast.LENGTH_LONG).show();
                }
                answer.setText("");
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cursor.moveToNext()) {
                } else {
                    cursor.moveToFirst();
                }
                updateTask();
            }
        });


    }

    private void updateTask() {
        if (cursor.getString(0) != null) {
            task.setText("<font size=5>" + cursor.getString(0) + "</font>" + "<br>");
        }
        answer.setText("");
    }
}

package com.example.vova.matesha;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wajahatkarim3.easyflipview.EasyFlipView;


import io.github.kexanie.library.MathView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class Card extends AppCompatActivity {

    EasyFlipView card;
    Button btnA, btnB, btnC, btnD, learn;
    MathView front, back;
    ImageView complite1, complite2, complite3, fail1, fail2;
    Intent intent;
    Cursor query;
    DBHelper helper;
    SQLiteDatabase db;
    boolean flag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_layout);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        intent = getIntent();
        learn = findViewById(R.id.learn_btn);
        card = findViewById(R.id.flip_card);
        btnA = findViewById(R.id.A);
        btnB = findViewById(R.id.B);
        btnC = findViewById(R.id.C);
        btnD = findViewById(R.id.D);
        front = findViewById(R.id.front_side);
        back = findViewById(R.id.back_side);
        complite1 = findViewById(R.id.complit_1);
        complite2 = findViewById(R.id.complit_2);
        complite3 = findViewById(R.id.complit_3);
        fail1 = findViewById(R.id.fail_1);
        fail2 = findViewById(R.id.fail_2);


        btnA.setOnClickListener(onClick);
        btnB.setOnClickListener(onClick);
        btnC.setOnClickListener(onClick);
        btnD.setOnClickListener(onClick);
        learn.setOnClickListener(onClick);


        helper = new DBHelper(this);
        db = helper.getReadableDatabase();

        updateCard();

    }

    private void updateCard() {
        flag = true;
        boolean doIt = true;
        if (query == null || query.isBeforeFirst() || query.isAfterLast()) {
            query = db.rawQuery("SELECT * FROM card WHERE chapter = ? AND corect_answer<3 ORDER by random() ", new String[]{intent.getStringExtra("CHUPTER")});
            if (query.getCount() != 0) {
                query.moveToFirst();
            } else doIt = !doIt;
        }
        if (doIt) {

            btnA.setEnabled(true);
            btnB.setEnabled(true);
            btnC.setEnabled(true);
            btnD.setEnabled(true);

            btnA.setBackgroundResource(android.R.drawable.btn_default);
            btnB.setBackgroundResource(android.R.drawable.btn_default);
            btnC.setBackgroundResource(android.R.drawable.btn_default);
            btnD.setBackgroundResource(android.R.drawable.btn_default);
            learn.setText(R.string.learn);

            btnA.setText(query.getString(3));
            btnB.setText(query.getString(4));
            btnC.setText(query.getString(5));
            btnD.setText(query.getString(6));
            front.setText(query.getString(2));
            back.setText(query.getString(1));

            switch (query.getInt(8)) {
                case 0: {
                    complite1.setImageResource(R.drawable.circle);
                    complite2.setImageResource(R.drawable.circle);
                    complite3.setImageResource(R.drawable.circle);
                    break;
                }
                case 1: {
                    complite1.setImageResource(R.drawable.green_circle);
                    complite2.setImageResource(R.drawable.circle);
                    complite3.setImageResource(R.drawable.circle);
                    break;
                }
                case 2: {
                    complite1.setImageResource(R.drawable.green_circle);
                    complite2.setImageResource(R.drawable.green_circle);
                    complite3.setImageResource(R.drawable.circle);
                }
            }
            if (query.getInt(9) == 1) {
                fail1.setImageResource(R.drawable.red_circle);
            } else {
                fail1.setImageResource(R.drawable.circle);
                fail2.setImageResource(R.drawable.circle);
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Card.this);
            builder.setTitle("Congratulations!")
                    .setMessage("Congratulations!")
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                            }).setPositiveButton("Повторити", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SQLiteDatabase database = helper.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("wrong_answer", 0);
                    contentValues.put("corect_answer", 0);
                    database.update("card", contentValues, "chapter = ?", new String[]{intent.getStringExtra("CHUPTER")});
                    updateCard();
                    dialogInterface.cancel();
                }
            })
            ;
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.A: {
                    Log.e("BtnClecked", "A");
                    card.flipTheView();
                    checkAnswer(view.getId());
                    learn.setText(R.string.next);
                    flag = false;
                    break;
                }
                case R.id.B: {
                    Log.e("BtnClecked", "B");
                    card.flipTheView();
                    checkAnswer(view.getId());
                    learn.setText(R.string.next);
                    flag = false;
                    break;
                }
                case R.id.C: {
                    Log.e("BtnClecked", "C");
                    card.flipTheView();
                    checkAnswer(view.getId());
                    learn.setText(R.string.next);
                    flag = false;
                    break;
                }
                case R.id.D: {
                    Log.e("BtnClecked", "D");
                    card.flipTheView();
                    checkAnswer(view.getId());
                    learn.setText(R.string.next);
                    flag = false;
                    break;
                }
                case R.id.learn_btn: {
                    card.flipTheView();
                    if (flag) {
                        learn.setText(R.string.next);
                        btnA.setEnabled(false);
                        btnB.setEnabled(false);
                        btnC.setEnabled(false);
                        btnD.setEnabled(false);
                        flag = false;
                    } else {
                        query.moveToNext();
                        updateCard();
                        Log.e("FLAG=FALSE", "updatecard();");
                    }
                    break;
                }
            }

        }

        private void checkAnswer(int id) {
            int correct = query.getInt(10);
            int complite = query.getInt(8);
            int fail = query.getInt(9);
            switch (correct) {
                case 1: {
                    if (id == R.id.A) {
                        btnA.setBackgroundResource(R.color.green);
                        switch (complite) {
                            case 0:
                                complite1.setImageResource(R.drawable.green_circle);
                                break;
                            case 1:
                                complite2.setImageResource(R.drawable.green_circle);
                                break;
                            case 2:
                                complite3.setImageResource(R.drawable.green_circle);
                                break;
                        }
                        updateBd(complite, true);
                    } else {
                        btnA.setBackgroundResource(R.color.green);
                        View view = findViewById(id);
                        view.setBackgroundResource(R.color.red);
                        switch (fail) {
                            case 0:
                                fail1.setImageResource(R.drawable.red_circle);
                                break;
                            case 1:
                                fail2.setImageResource(R.drawable.red_circle);
                                break;
                        }
                        updateBd(fail, false);
                    }
                    break;
                }
                case 2: {
                    if (id == R.id.B) {
                        btnB.setBackgroundResource(R.color.green);
                        switch (complite) {
                            case 0:
                                complite1.setImageResource(R.drawable.green_circle);
                                break;
                            case 1:
                                complite2.setImageResource(R.drawable.green_circle);
                                break;
                            case 2:
                                complite3.setImageResource(R.drawable.green_circle);
                                break;
                        }
                        updateBd(complite, true);

                    } else {
                        btnB.setBackgroundResource(R.color.green);
                        View view = findViewById(id);
                        view.setBackgroundResource(R.color.red);
                        switch (fail) {
                            case 0:
                                fail1.setImageResource(R.drawable.red_circle);
                                break;
                            case 1:
                                fail2.setImageResource(R.drawable.red_circle);
                                break;
                        }
                        updateBd(fail, false);
                    }
                    break;
                }
                case 3: {
                    if (id == R.id.C) {
                        btnC.setBackgroundResource(R.color.green);
                        switch (complite) {
                            case 0:
                                complite1.setImageResource(R.drawable.green_circle);
                                break;
                            case 1:
                                complite2.setImageResource(R.drawable.green_circle);
                                break;
                            case 2:
                                complite3.setImageResource(R.drawable.green_circle);
                                break;
                        }
                        updateBd(complite, true);
                    } else {
                        btnC.setBackgroundResource(R.color.green);
                        View view = findViewById(id);
                        view.setBackgroundResource(R.color.red);
                        switch (fail) {
                            case 0:
                                fail1.setImageResource(R.drawable.red_circle);
                                break;
                            case 1:
                                fail2.setImageResource(R.drawable.red_circle);
                                break;
                        }
                        updateBd(fail, false);
                    }
                    break;
                }
                case 4: {
                    if (id == R.id.D) {
                        btnD.setBackgroundResource(R.color.green);
                        switch (complite) {
                            case 0:
                                complite1.setImageResource(R.drawable.green_circle);
                                break;
                            case 1:
                                complite2.setImageResource(R.drawable.green_circle);
                                break;
                            case 2:
                                complite3.setImageResource(R.drawable.green_circle);
                                break;
                        }
                        updateBd(complite, true);
                    } else {
                        btnD.setBackgroundResource(R.color.green);
                        View view = findViewById(id);
                        view.setBackgroundResource(R.color.red);
                        switch (fail) {
                            case 0:
                                fail1.setImageResource(R.drawable.red_circle);
                                break;
                            case 1:
                                fail2.setImageResource(R.drawable.red_circle);
                                break;
                        }
                        updateBd(fail, false);

                    }
                    break;
                }
            }
            btnA.setEnabled(false);
            btnB.setEnabled(false);
            btnC.setEnabled(false);
            btnD.setEnabled(false);
        }
    };


    private void updateBd(int result, boolean correct) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (!correct) {
            if (result + 1 == 2) {
                contentValues.put("wrong_answer", 0);
                contentValues.put("corect_answer", 0);
                complite1.setImageResource(R.drawable.circle);
                complite2.setImageResource(R.drawable.circle);
            } else contentValues.put("wrong_answer", 1);
        } else {
            contentValues.put("corect_answer", result + 1);
        }
        db.update("card", contentValues, "_id=?", new String[]{query.getInt(0) + ""});
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT corect_answer, wrong_answer FROM card WHERE _id = ?", new String[]{query.getInt(0) + ""});
        cursor.moveToFirst();
        Log.e("corect_answer", cursor.getInt(0) + "");
        Log.e("wrong_answer", cursor.getInt(1) + "");
    }
}

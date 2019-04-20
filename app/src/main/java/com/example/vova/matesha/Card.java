package com.example.vova.matesha;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
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


        DBHelper helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        query = db.rawQuery("SELECT * FROM card WHERE chapter = ? ORDER by random() ", new String[]{intent.getStringExtra("CHUPTER")});

        query.moveToFirst();
        updateCard();

    }

    private void updateCard() {
        btnA.setText(query.getString(3));
        btnB.setText(query.getString(4));
        btnC.setText(query.getString(5));
        btnD.setText(query.getString(6));
        front.setText(query.getString(2));
        back.setText(query.getString(1));
        switch (query.getInt(8)) {
            case 1: {
                complite1.setImageResource(R.drawable.green_circle);
                break;
            }
            case 2: {
                complite1.setImageResource(R.drawable.green_circle);
                complite2.setImageResource(R.drawable.green_circle);
            }
        }
        if (query.getInt(9) == 1) {
            fail2.setImageResource(R.drawable.red_circle);
        }
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.A: {
                    card.flipTheView();
                    checkAnswer(view.getId());
                    break;
                }
                case R.id.B: {
                    card.flipTheView();
                    checkAnswer(view.getId());
                    break;
                }
                case R.id.C: {
                    card.flipTheView();
                    checkAnswer(view.getId());
                    break;
                }
                case R.id.D: {
                    card.flipTheView();
                    checkAnswer(view.getId());
                    break;
                }
                case R.id.learn_btn: {
                    card.flipTheView();
                    query.moveToNext();
                    break;
                }
            }
        }

        private void checkAnswer(int id) {
        int correct = query.getInt(10);
        switch (correct){
            case 1:{
                if(id==R.id.A){
                    btnA.setBackgroundResource(R.color.green);
                }
                else{
                    btnA.setBackgroundResource(R.color.red);
                    View view = findViewById(id);
                    view.setBackgroundResource(R.color.green);
                }
                break;
            }
            case 2:{
                if(id==R.id.B){
                    btnA.setBackgroundResource(R.color.green);
                }
                else{
                    btnA.setBackgroundResource(R.color.red);
                    View view = findViewById(id);
                    view.setBackgroundResource(R.color.green);
                }
                break;
            }
            case 3:{
                if(id==R.id.C){
                    btnA.setBackgroundResource(R.color.green);
                }
                else{
                    btnA.setBackgroundResource(R.color.red);
                    View view = findViewById(id);
                    view.setBackgroundResource(R.color.green);
                }
                break;
            }
            case 4:{
                if(id==R.id.D){
                    btnA.setBackgroundResource(R.color.green);
                }
                else{
                    btnA.setBackgroundResource(R.color.red);
                    View view = findViewById(id);
                    view.setBackgroundResource(R.color.green);
                }
                break;
            }
        }
        }
    };

}

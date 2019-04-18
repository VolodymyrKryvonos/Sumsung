package com.example.vova.matesha;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import io.github.kexanie.library.MathView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class ChoiseAction extends AppCompatActivity {
    Intent intent;
    Button learnBtn, theoriBtn, testsBtn;
    DBHelper helper;
    EasyFlipView card;
    MathView front, back;
    public final static String INTENT_KEY = "FROM BTN";
    public final static String SUB_KEY = "ALG/GEOM";
    boolean side = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_action);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        final Cursor query = db.rawQuery("SELECT shortTheory FROM learn ORDER BY RANDOM()", null);
        front = findViewById(R.id.front_side);
        back = findViewById(R.id.back_side);
        learnBtn = findViewById(R.id.learn);
        theoriBtn = findViewById(R.id.theori);
        testsBtn = findViewById(R.id.tests);
        card = findViewById(R.id.flip_card);

        if (query.moveToFirst()) {
            front.setText(query.getString(0));
        }

        query.moveToNext();
        back.setText(query.getString(0));

        card.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                if (!query.isLast()) {
                    if (side) {
                        side = !side;
                        if (query.moveToNext()) {
                            back.setText(query.getString(0));
                        }
                    } else {
                        side = !side;
                        if (query.moveToNext()) {
                            front.setText(query.getString(0));
                        }
                    }
                }
                else if(side){
                    query.moveToFirst();
                    side = !side;
                    back.setText(query.getString(0));
                }
                else {
                    query.moveToFirst();
                    side = !side;
                    front.setText(query.getString(0));
                }
            }

        });

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ChoiseAction.this, ChoseChapter.class);
                switch (v.getId()) {
                    case R.id.theori:
                        intent.putExtra(INTENT_KEY, 1);
                        startActivity(intent);
                        return;
                    case R.id.learn:
                        intent.putExtra(INTENT_KEY, 2);
                        startActivity(intent);
                        return;
                    case R.id.tests:
                        intent.putExtra(INTENT_KEY, 3);
                        startActivity(intent);
                        return;


                }
                startActivity(intent);
            }
        };

        learnBtn.setOnClickListener(onClick);
        theoriBtn.setOnClickListener(onClick);
        testsBtn.setOnClickListener(onClick);

    }
}

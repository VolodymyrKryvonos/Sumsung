package com.example.vova.matesha;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import io.github.kexanie.library.MathView;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

public class ChoiseAction extends AppCompatActivity {
    Intent intent;
    Button theoriBtn, znoBtn, resultBtn;
    DBHelper helper;
    EasyFlipView card;
    Toolbar toolbar;
    MathView front, back;
    public final static String INTENT_KEY = "FROM BTN";
    boolean side = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_action);
        setRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
        helper = new DBHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        final Cursor query = db.rawQuery("SELECT shortTheory FROM learn ORDER BY RANDOM()", null);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbarTextView);
        textView.setText(R.string.full_project_name);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        front = findViewById(R.id.front_side);
        back = findViewById(R.id.back_side);
        theoriBtn = findViewById(R.id.theori);
        znoBtn = findViewById(R.id.ZNO);
        resultBtn = findViewById(R.id.result);

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
                switch (v.getId()) {
                    case R.id.theori:
                        intent = new Intent(ChoiseAction.this, ChoseChapter.class);
                        intent.putExtra(INTENT_KEY, 1);
                        break;
                    case R.id.ZNO:
                        intent = new Intent(ChoiseAction.this, ZnoCard.class);
                        intent.putExtra(INTENT_KEY, 2);
                        break;
                }
                startActivity(intent);
            }
        };
        znoBtn.setOnClickListener(onClick);
        theoriBtn.setOnClickListener(onClick);
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChoiseAction.this, ResultActivity.class);
                startActivity(intent);
            }
        });

    }
}

package com.example.vova.matesha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ChoiseAction extends AppCompatActivity {
    Intent intent;
    Button learnBtn, theoriBtn, testsBtn;
    public final static String INTENT_KEY = "FROM BTN";
    public final static String SUB_KEY = "ALG/GEOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_action);

        learnBtn = findViewById(R.id.learn);
        theoriBtn = findViewById(R.id.theori);
        testsBtn = findViewById(R.id.tests);

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

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
                        showPopupMenu(v);
                        return;
                    case R.id.learn:
                        intent.putExtra(INTENT_KEY, 2);
                        showPopupMenu(v);
                        return;
                    case R.id.tests:
                        intent.putExtra(INTENT_KEY, 3);
                        showPopupMenu(v);
                        return;
                    default:

                }

            }
        };

        learnBtn.setOnClickListener(onClick);
        theoriBtn.setOnClickListener(onClick);
        testsBtn.setOnClickListener(onClick);

    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(ChoiseAction.this, v);
        popupMenu.inflate(R.menu.popup);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.algebra:
                        intent.putExtra(SUB_KEY, 0);
                        startActivity(intent);
                        return true;
                    case R.id.geometry:
                        intent.putExtra(SUB_KEY, 1);
                        startActivity(intent);
                        return true;
                    default:
                        return false;
                }

            }
        });

        popupMenu.show();
    }
}

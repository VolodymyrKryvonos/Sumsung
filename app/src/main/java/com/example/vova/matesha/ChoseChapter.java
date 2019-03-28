package com.example.vova.matesha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChoseChapter extends AppCompatActivity {

    Intent intentFromChoser = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chose_chapter);

    }
}

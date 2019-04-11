package com.example.vova.matesha;

import android.support.v4.app.Fragment;

public class FlipCardActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new Card();
    }
}
